this application could be used for debug Google Cloud Messages

<img src="https://raw.githubusercontent.com/Flinbor/sample-GCM/gh-pages/device-2015-11-18-153941.png" alt="home screen" width="200" height="400">

App has two logical modules:
<ol>
<li>client part</li>
<li>server-like part</li>
</ol>

preparing:
<ul>
<li>Created project in Google Developers console</li>
<li>Generated Project number - used for registration in Google Api system as client.</li>
<li>Create server API key - used by server side</li>
</ul>

<p><b>Client part</b></p>
<ol>
<li>Client creates request to GCA Api for get Token</li>
<li>Stored it for use in "server-like-part" of application
<p><i>[in real project should send this token to out Backhand]</p></i></li>
<li>start listen broadcast GCM</li>
</ol>

Client communicate with Google Coud Api for retreive Token.
Client listens notifications from server in Service Broadcast Receiver

Applicaiton in role "server"
For emulation push (like message created from server) 
application uses server API key - generated  in Dev Console
application send POST to Google cloud with:
<ul>
<li>API key</li>
<li>message</li>
<li>token (this token directly taken from the app after registration to Coud, but in real server-client application the token should be sent to server after client registration to GCM</li>
</ul>

Google cloud creates Push Message and send it to client

UI:

<ul>
<li>Log</li>
<li>Show messages sent to server</li>
<li>Show cont of successfuly sent messages</li>
<li>Show messages received from server</li>
</ul>

Message to server could be created in application: add text to send in special field and press  send.

Note:
messages could be received by client with some delay
Google not guarantee thet message will be sent at the same time, there exist same optimization on server side and messages could be received in scope.
In case some messages was send when client was offline, client will receive it as soon as connection will appear and client will register Token. 
