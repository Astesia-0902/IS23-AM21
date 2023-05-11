package org.am21.model.chat;

import org.am21.controller.CommunicationController;
import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to manage the chat messages
 * Every match has a chat manager
 */
public class ChatManager{
    public List<String> chatMessages = new ArrayList<>();
    private Match match;

    // 6 Private Chats, one for each couple of players
    private List<List<String>> privateChats = new ArrayList<>();
    private HashMap<String, Integer> chatMap = new HashMap<>();

    public ChatManager(Match match) {
        this.match = match;
    }

    public List<List<String>> getPrivateChats() {
        return privateChats;
    }

    public HashMap<String, Integer> getChatMap() {
        return chatMap;
    }

    /**
     * send the message to all players
     *
     * @param message the message
     * @param sender  the sender
     * @throws RemoteException if the remote call fails
     */
    /*public void sendMessage(String message, String sender) throws RemoteException {
        this.message = message;
        this.sender = sender;
        chatMessages.add(sender + ": " + message);
        for (Player player : match.playerList) {
            GameController.sendChatMessage(chatMessages.get(chatMessages.size() - 1), player.getController());
        }
    }*/

    /**
     * Get the chat messages history
     *
     * @return the chat messages history
     */
    public List<String> getChatMessages() {
        return chatMessages;
    }

    public void sendChat(String message, String sender) {
        //this.message = message;
        //this.sender = sender;
        chatMessages.add(sender + ": " + message);
        String lastTenMex = "";
        int tmp = chatMessages.size();

        for (int t = 0; t < 10; t++) {
            if (tmp > 0) {
                tmp--;
            }
        }
        lastTenMex += "\n< Recent Chat Messages >\n";
        for (int i = tmp; i < chatMessages.size(); i++) {
            lastTenMex += chatMessages.get(i);
            if (i == chatMessages.size() - 1) {
                lastTenMex += " >> NEW\n\nPress 'Enter'";
            }
            lastTenMex += "\n";

        }
        for (Player p : match.playerList) {
            CommunicationController.instance.sendMessageToClient(lastTenMex, true, p.getController());
        }

    }

    public void handlePublicChatMessage(Player sender, String message){
        chatMessages.add(sender.getNickname() + ": " + message);
        if(chatMessages.size()>50){
            chatMessages.remove(0);
        }
    }


    public boolean handlePrivateChatMessage(Player sender, String receiver, String message) {
        //Check sender and receiver
        Player p_receiver = isMember(receiver);
        if(sender.getNickname().equals(receiver) || !match.playerList.contains(sender) || p_receiver==null){
            return false;
        }
        //Check if private chat exists
        String key = getChatKey(sender.getNickname(),receiver);
        String messageLine = sender.getNickname() + " > " + message;

        if(!chatMap.containsKey(key) && !createNewPrivateChat(sender,p_receiver)){
            return  false;
        }
        int chatNum = chatMap.get(key);
        privateChats.get(chatNum).add(messageLine);

        return true;
    }

    public Player isMember(String playerName){
        for(Player  p:match.playerList){
            if(p.getNickname().equals(playerName)){
                return p;
            }
        }
        return null;
    }

    public String getChatKey(String name1,String name2){
       return  (name1.compareTo(name2)) < 0 ? (name1 + "&" + name2) : (name2 + "&" + name1);

    }

    public boolean createNewPrivateChat(Player p1, Player p2) {
        //If the player are not in the same match or have the same name
        if (!p1.getMatch().equals(p2.getMatch()) || p1.getNickname().equals(p2.getNickname())) {
            return false;
        }
        String name1 = p1.getNickname();
        String name2 = p2.getNickname();
        String key = (name1.compareTo(name2)) < 0 ? (name1 + "&" + name2) : (name2 + "&" + name1);
        if (chatMap.containsKey(key)) {
            return false;
        }
        privateChats.add(new ArrayList<>());
        chatMap.put(key, privateChats.size() - 1);

        return true;
    }

    /**
     * Return a string that contains the chat history of a specific chat (private or public)
     * //TODO: wrong redo, no need for controller and receiver: just convert the chat in a string
     * Input : List .output:String
     * @param ctrl
     * @param receiver
     * @return String
     */
    public String openChat(PlayerController ctrl,String receiver){
        List<String> chat = findChat(ctrl.getPlayer().getNickname(),receiver);
        if(chat == null){
            return "Chat not found";
        }
        String lastMex = "-------------------------------------------------------";
        int tmp = chat.size();
        if(receiver.equals(""))
            receiver="Match";
        for (int t = 0; t < 25; t++) {
            if (tmp > 0) {
                tmp--;
            }
        }
        lastMex += "\n< "+receiver+" Chat >\n";
        for (int i = tmp; i < chat.size(); i++) {
            lastMex += chat.get(i);
            if (i == chat.size() - 1) {
                lastMex += "      <<<\n";
            }
            lastMex += "----------------------------------------------------------";

        }
        //CommunicationController.instance.sendMessageToClient(lastMex, false,ctrl);
        return lastMex;
    }


    /**
     * Input: sender and receiver
     * @param sender
     * @param receiver
     * @return The list of the chat
     */
    public List<String> findChat(String sender,String receiver){
        if(receiver.equals("")){
            return chatMessages;
        }

        String key = getChatKey(sender,receiver);
        if(chatMap.containsKey(key)){
            int chatNum = chatMap.get(key);
            return privateChats.get(chatNum);
        }else{
            Player p1 = isMember(sender);
            Player p2 = isMember(receiver);
            if(p1!=null && p2!=null && createNewPrivateChat(p1,p2)){
                return privateChats.get(chatMap.get(key));
            }
        }
        return null;
    }






}
