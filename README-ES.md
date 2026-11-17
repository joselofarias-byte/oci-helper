# OCI Helper en español — JolufaSoft

Panel web para Oracle Cloud basado en OCI Helper 3.5.1, revisión original `f74709594727c17298d0e2a46b286bcc915b00d4`. Licencia Apache 2.0; se mantienen la licencia y la atribución original.

## Instalación en Oracle Ubuntu con Docker

```bash
sudo bash <<'SH'
set -Eeuo pipefail
if mountpoint -q /mnt/datos; then DESTINO=/mnt/datos/oci-helper-es; else DESTINO=/opt/oci-helper-es; fi
if [ ! -e "$DESTINO" ]; then
  git clone --single-branch --branch jolufasoft/es-seguro https://github.com/joselofarias-byte/oci-helper.git "$DESTINO"
fi
cd "$DESTINO"
bash scripts/instalar-es.sh
SH
```

El instalador genera credenciales distintas, conserva los datos en volúmenes Docker y solo publica `127.0.0.1:8818`. Necesita Docker con el complemento Compose, Git, curl y OpenSSL. La primera compilación consume espacio y memoria; utiliza `/mnt/datos` cuando está montado. Una instancia A1 de 12 GB tiene margen para la compilación, pero no se midieron tiempos ni consumo en ARM.

Para abrirlo desde el HONOR por la red privada de Tailscale, ejecuta en Oracle:

```bash
sudo tailscale serve --bg http://127.0.0.1:8818
sudo tailscale serve status
```

Las credenciales están en `deploy/.env`, con permisos 600. Puedes consultarlas localmente con `sudo cat deploy/.env`; el archivo también contiene la clave JWT y debe permanecer privado. Activa MFA desde Ajustes del sistema después del primer acceso. Si Tailscale Serve ya tiene otra configuración en el mismo puerto, revisa esa configuración antes de ejecutar el comando.

Para detener o consultar registros, desde el repositorio:

```bash
sudo docker compose --env-file deploy/.env -f deploy/compose.es.yml logs --tail 100 panel
sudo docker compose --env-file deploy/.env -f deploy/compose.es.yml stop
```

Para actualizar, conserva `deploy/.env` y los volúmenes, actualiza esta rama con `git pull --ff-only` y vuelve a ejecutar el instalador. No ejecutes el instalador original: volvería a instalar la distribución del autor.

## Traducción

- 753 frases de la interfaz principal: navegación, acceso, instancias, tareas, redes, volúmenes, cuotas, costes, Cloudflare, copias, formularios y confirmaciones.
- 260 frases del backend: validaciones, respuestas y errores de API.
- Nombres de regiones y descripciones de enumeraciones en español; los identificadores OCI permanecen iguales.
- Controles de Element Plus en español, incluidos calendario, tablas, paginación y diálogos.
- Página de consulta IP en español, sin consultas automáticas al cargar.

La distribución original incluye el frontend compilado, sin fuentes Vue. Se conserva ese frontend y se traducen literales mediante un analizador JavaScript. Los glosarios quedan en `localization/` y la comprobación se ejecuta con `npm ci --ignore-scripts && npm run check:locale`. Los módulos de terceros no se reescriben; Element Plus recibe explícitamente su locale español.

La traducción de esta entrega cubre el panel y las respuestas API. El bot Telegram y los registros internos conservan mensajes originales en chino e inglés; no se presentan como traducción completa de esos subsistemas. Los datos escritos por el usuario, respuestas de Oracle y modelos de IA conservan su idioma.

## Cambios de seguridad

- Difusión de creación hacia el backend del autor desactivada por defecto, y geolocalización automática del servidor desactivada.
- Sin contraseñas root ni credenciales del proxy en los registros identificados durante la revisión.
- Se mantienen las notificaciones privadas de creación y rescate al Telegram o DingTalk configurado por el administrador; pueden incluir la contraseña necesaria para acceder a la instancia.
- Sin nuevas contraseñas en etiquetas de OCI. La eliminación de etiquetas antiguas sigue disponible; guardar contraseñas en etiquetas se rechaza y su botón está desactivado. Esa función original editaba una etiqueta, no cambiaba la contraseña del sistema operativo.
- Clave JWT independiente, validación estricta de JSON, firma HS256 y vencimiento obligatorio.
- Inicio bloqueado con usuario predeterminado, contraseña corta o clave JWT reutilizada.
- Archivos de claves subidos con nombre generado por el servidor, límite de 64 KiB y permisos 600 en Linux.
- Cabeceras de IP proporcionadas por el cliente ignoradas. Con un proxy, la limitación de accesos se aplica a la IP del proxy; se prioriza el acceso privado.
- Conexiones de registros sin token cerradas y sin detener la sesión autorizada.
- CORS abierto eliminado; WebSocket de registros con política de origen del mismo sitio.
- Actualización original desactivada. No hay watcher ni montaje del socket Docker.
- Contenedor con usuario 10001, raíz de solo lectura, sin capacidades, registros rotativos y volúmenes persistentes.

## Validación y límites

Compilación Java 21 y 31 pruebas de regresión de JWT, autorización, carga de claves y credenciales de inicio. Pruebas reales de SQLite (SQL en memoria, escritura y reapertura de archivo, presencia de bibliotecas de ambas arquitecturas). Arranque local real con SQLite, acceso con usuario/contraseña, consulta autenticada, rechazo HTTP 401 sin token y bloqueo del actualizador comprobados. Sintaxis shell y parseo de los módulos JavaScript verificados.

No se ejecutaron operaciones contra tu cuenta Oracle. El primer despliegue ARM64 compiló pero falló al cargar SQLite. La revisión es2 extrae la biblioteca nativa correspondiente a la arquitectura durante la construcción y la carga desde `/opt/sqlite-native`, sin depender de extraerla en `/tmp`. El temporal del contenedor tiene permisos 1777 y ejecución explícita para otras bibliotecas JNI. Se añaden pruebas CI de SQLite y del arranque del contenedor en x86_64 y ARM64. La representación visual en navegador sigue pendiente: la descarga del navegador de pruebas falló. La consola VNC necesita un websockify adicional y su proxy; no se incluye un puerto público 6080 en este despliegue. Las funciones de rescate/reducción de discos y 500 Mbps conservan las limitaciones del proyecto original y no fueron probadas contra OCI.

SQLite, archivos PEM y copias sin cifrar siguen conteniendo información sensible. El cifrado de copias es una opción del panel; no hay cifrado completo de la base de datos. Una instalación nueva no borra secretos ya existentes en etiquetas OCI, registros o copias previas. Esta revisión focalizada no equivale a una auditoría exhaustiva de todo el SDK y del bot.
