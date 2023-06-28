package org.am21.model.chat;

import org.am21.model.Match;
import org.am21.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage the chat messages
 * Every match has a chat manager
 */
public class ChatManager{
    public List<String> publicChatMessages = new ArrayList<>();
    /**
     * This method is used to add a line of message into the match group chat
     * @param sender player who sent the message
     * @param message message
     */
    public void handlePublicChatMessage(Player sender, String message){
        publicChatMessages.add(sender.getNickname() + ": " + message);
        if(publicChatMessages.size()>50){
            publicChatMessages.remove(0);
        }
    }
















}
