# 3rdYearProject
Finding and Visualising Edits to Strings


### Configuration
The StringEdis library requires a docker container running for couchbase databse (I used kitematic docker and configured ports accordingly to the ones provided)

From there you need to access either localhost:8091 (their administrator interface with user: Administrator and pwd: administrator)

If localhost fails such as on Windows machines, configure a host name for it to be under local.couchbase (:8091)

From here create a new user such as `test` with same password and all permissions for easy testing then create 2 buckets with the following names:
  -  dictionary
  -  resultsCache
  
 Now go the the querry tool and insert the following lines:
 
  > CREATE INDEX index1 ON \`dictionary\`(name);
  
  > CREATE PRIMARY INDEX \`primary-index\` ON \`dictionary\` USING GSI;
 
 >  CREATE INDEX index1 ON \`resultsCache\`(hashcode);
 
 >  CREATE PRIMARY INDEX \`primary-index\` ON \`resultsCache\` USING GSI;
 
 ### Running the Project
 I used Intellij for development of this spring app and library.
 
 The library itself can be found in `StringEdits-1.0.0.jar`
 
 To run the Test Interface either run `java -jar TestInterface-1.0.0.jar` that will start the spring boot app then access localhost:8080
 
 When accessing the interface go the the load screen and create a few languages with some entries. There are also available 3 files for test to preload (english, eng and ro).
 
 There is the alternative to run in REST style the app and get JSON output from the library results by hitting the controllers in the LanguageController.java or DictionaryController.java
 
 
 The controller also takes 2 optional parameters such as the search algorithm(STANDARD, TRANSPOSITION, MERGE_AND_SPLIT) used or the maximum distance which needs to be a positive integer.
 Examples:
 > `localhost:8080/getMatches/eng/inclapable`

 > `localhost:8080/getMatches/eng/inclapable?algorithm=TRANSPOSITION&maxDistance=8`
 
 The test interface is for demo purpose as the **StringEdits** library was the main focus of this project.
 The interface app provides a formated view of the TermQuery result for various type searches for single word multilanguage or multiwords in multiple languages.
