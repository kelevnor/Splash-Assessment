# Splash-AssessmentApplication built for Assessment with Splash. The application consumes an api and shows a list of messages to the user. 
The application requests permissions in runtime if it detects operating system _M  and above. Added this because I thought images would exist on the message list.
On MainActivity it consumes the REST API provided and stores the result in a List<Message>. To accomplish this I am using Retrofit 2 and Gson respectively. My Message object implements comparable and I compare the sent_at key attribute to sort by sent_at date. Was the logical thing to do at that point.
User can click on the inbox icon on the actionbar to navigate to their inbox.
Tried to follow the design here and added the tabs to imitate action.
Sender name and first line of message are shown for each item, along with the date sent. Date sent is converted to days if more than 24 hours have passed, specifying if message was sent ahead or before current timestamp.
Users can navigate and view individual messages, with the full date of the sent_at timestamp.
Created very basic custom Dialog layout for the initial permissions flow.
