application for debug Google Cloud Messages

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
<li>Client listens notifications from server in Service BroadcastReceiver</li>
</ol>

<p><b>Server-like part</b></p>

For emulation push (like message created from server) 
application uses server API key - generated  in Dev Console

Send POST to Google Cloud API with:
<ul>
<li>Server API Key</li>
<li>Message</li>
<li>Token - token of client which should be notified</li>
</ul>

Google Cloud creates Push Message and send it to clients
Google Cloud API response with count of successfully notified clients


<p><b>UI</p></b>

<ul>Input field - text that will be sent to GCM</ul>
<ul>Button for send push</ul>
<ul>Status of registration to GCM (green mark - success or red - not registered)</ul>
<ul>Log</ul>
<ul>
<li>Show messages sent to server</li>
<li>Show cont of successfuly sent messages</li>
<li>Show messages received from server</li>
</ul>
</ul>

<p><b>Note</p></b>
messages could be received by client with some delay
Google not guarantee the message will be sent at the same time, there exist same optimization on server side and messages could be received in scope.
In case some messages was send when client was offline, client will receive it as soon as connection will appear and client will register Token.
