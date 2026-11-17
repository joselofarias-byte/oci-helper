#!/usr/bin/env bash
set -Eeuo pipefail
IMAGE="${1:-oci-helper-es:3.5.1-es2}"
NAME="oci-helper-es-smoke-$$"
cleanup() { docker rm -fv "$NAME" >/dev/null 2>&1 || true; }
trap cleanup EXIT
# Explicit library loading must work even with a non-executable temporary mount.
docker run --rm --read-only --cap-drop ALL --security-opt no-new-privileges:true \
  --tmpfs /tmp:rw,noexec,nosuid,nodev,mode=1777,size=64m \
  --entrypoint java "$IMAGE" \
  -Dorg.sqlite.lib.path=/opt/sqlite-native -Dorg.sqlite.lib.name=libsqlitejdbc.so \
  -Dloader.main=com.yohann.ocihelper.utils.SQLiteNativeSmoke \
  -cp /opt/oci-helper.jar org.springframework.boot.loader.launch.PropertiesLauncher
# Check real application startup with the deployment restrictions and fresh data.
docker run -d --name "$NAME" --read-only --cap-drop ALL \
  --security-opt no-new-privileges:true --memory 1536m --pids-limit 256 \
  --tmpfs /tmp:rw,exec,nosuid,nodev,mode=1777,size=256m \
  --mount type=volume,target=/app/oci-helper \
  --mount type=volume,target=/home/ocihelper/.ssh \
  -p 127.0.0.1::8818 \
  -e OCI_HELPER_ACCOUNT=prueba-local \
  -e OCI_HELPER_PASSWORD=smoke-test-password-with-32-characters \
  -e OCI_HELPER_JWT_SECRET=smoke-test-independent-signing-key-with-48-characters \
  "$IMAGE" >/dev/null
PORT="$(docker port "$NAME" 8818/tcp | head -n 1)"
for ((attempt=1;attempt<=60;attempt++)); do
  if curl --noproxy '*' -fsS --max-time 2 "http://$PORT/" >/dev/null 2>&1; then
    curl --noproxy '*' -fsS --max-time 10 \
      -H 'Content-Type: application/json' \
      --data '{"account":"prueba-local","password":"smoke-test-password-with-32-characters"}' \
      "http://$PORT/api/sys/login" |
      python3 -c 'import json,sys; r=json.load(sys.stdin); assert r.get("success") and r["data"]["token"]; print("CONTAINER_LOGIN_OK")'
    exit 0
  fi
  if [[ "$(docker inspect -f '{{.State.Running}}' "$NAME")" != true ]]; then break; fi
  sleep 2
done
docker logs --tail 250 "$NAME"
echo 'Falló la comprobación del contenedor' >&2
exit 1
