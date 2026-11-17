#!/usr/bin/env bash
set -Eeuo pipefail
umask 077
REPO_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"
COMPOSE="$REPO_DIR/deploy/compose.es.yml"
ENV_FILE="$REPO_DIR/deploy/.env"
command -v docker >/dev/null || { echo 'Instala Docker y Docker Compose antes de continuar.' >&2; exit 1; }
docker compose version >/dev/null
docker info >/dev/null
if [[ ! -f "$ENV_FILE" ]]; then
  command -v openssl >/dev/null || { echo 'Falta openssl para generar credenciales.' >&2; exit 1; }
  PASSWORD="$(openssl rand -hex 24)"
  JWT_SECRET="$(openssl rand -hex 48)"
  printf 'OCI_HELPER_ACCOUNT=jolufa\nOCI_HELPER_PASSWORD=%s\nOCI_HELPER_JWT_SECRET=%s\n' "$PASSWORD" "$JWT_SECRET" > "$ENV_FILE"
  chmod 600 "$ENV_FILE"
  unset PASSWORD JWT_SECRET
fi
chmod 600 "$ENV_FILE"
docker compose --env-file "$ENV_FILE" -f "$COMPOSE" config -q
docker compose --env-file "$ENV_FILE" -f "$COMPOSE" build --pull
docker compose --env-file "$ENV_FILE" -f "$COMPOSE" up -d --no-build
READY=0
for ((attempt=1;attempt<=60;attempt++)); do
  if curl --noproxy '*' --fail --silent --max-time 2 http://127.0.0.1:8818/ >/dev/null; then READY=1; break; fi
  sleep 2
done
if (( READY != 1 )); then
  docker compose --env-file "$ENV_FILE" -f "$COMPOSE" logs --tail 100 panel
  echo 'El panel no respondió. Consulta los registros anteriores.' >&2
  exit 1
fi
printf '\nPanel disponible en http://127.0.0.1:8818\n'
printf 'Credenciales locales: %s\n' "$ENV_FILE"
printf 'Para acceso privado desde tu HONOR mediante Tailscale:\n  sudo tailscale serve --bg http://127.0.0.1:8818\n'
printf 'Dirección privada (si Tailscale está instalado):\n'
if command -v tailscale >/dev/null; then tailscale serve status || true; fi
