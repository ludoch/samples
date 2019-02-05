echo "******** Deploy to prod *******"
#cd appengine-java11
GOOGLE_CLOUD_PROJECT=jfokus
for app in "bigtable" "cloudsql" "cloudsql-postgres" "gaeinfo" \
      "sparkjava" "helidon-quickstart-mp" "bigquery" "micronaut-hello-java11" \
      "springboot" "spanner" "smallest-fatjar" "smallest-sample" "springboot-thin" "springboot-springcloudgcp"
do
  (cd "${app}"
       mvn clean install appengine:deploy -Dapp.deploy.version="${app}" \
          -Dapp.deploy.force=true -Dapp.deploy.promote=false \
          -Dapp.deploy.projectId="${GOOGLE_CLOUD_PROJECT}" -DskipTests=true
	   echo "${app} done")
done
