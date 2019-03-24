# ChatApp
It is a chat application written in Java. There is one server and multiple clients.  A client can communicate with other clients via
the server. A client/sender sends the message to the server, which in turn multicasts message received from the sender to all other 
clients. There are two implementations of the application. One is with conventional socket programming, and another via Remote Method
invocation (RMI). In the former, each client maintains a TCP connection with the server as long as it is active in the chat, whereas 
in the latter, each client invokes server's multicast method via RMI to send a message to all other clients. 

Java AWT and Swing API used for GUI.
