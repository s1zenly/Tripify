#!/bin/sh
set -eu

sed \
  -e "s|\${TRIPIFY_ACCESS_COOKIE_NAME}|${TRIPIFY_ACCESS_COOKIE_NAME}|g" \
  /kong/prepare-auth.lua.tmpl > /tmp/prepare-auth.lua

cp /kong/inject-user-id.lua.tmpl /tmp/inject-user-id.lua

sed \
  -e "s|__TRIPIFY_JWT_SECRET__|${TRIPIFY_JWT_SECRET}|g" \
  /kong/kong.template.yaml > /tmp/kong.base.yaml

awk '
  /^__PREPARE_AUTH_LUA__$/ {
    while ((getline line < "/tmp/prepare-auth.lua") > 0) {
      print "          " line
    }
    close("/tmp/prepare-auth.lua")
    next
  }
  /^__INJECT_USER_ID_LUA__$/ {
    while ((getline line < "/tmp/inject-user-id.lua") > 0) {
      print "          " line
    }
    close("/tmp/inject-user-id.lua")
    next
  }
  { print }
' /tmp/kong.base.yaml > /tmp/kong.generated.yaml

export KONG_DECLARATIVE_CONFIG=/tmp/kong.generated.yaml
exec /docker-entrypoint.sh kong docker-start
