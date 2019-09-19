echo '打包项目...'
maven/bin/mvn clean package
java -jar target/calculator-jar-with-dependencies.jar