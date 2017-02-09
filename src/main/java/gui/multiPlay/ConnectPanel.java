package gui.multiPlay;


import game.interfaces.view.mulitiPlay.IConnectView;
import game.multiPlay.ClientThread;
import game.multiPlay.ConnUtil;
import game.multiPlay.ServerThread;
import gui.GameContext;
import org.jdesktop.swingx.JXTextField;
import sun.net.util.IPAddressUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ConnectPanel extends JPanel implements IConnectView{

    /**输出*/
    private JTextArea textArea;
    /**发送消息控件*/
    private JXTextField inputTxt;
    private JButton send;
    /**联机地址相关*/
    private JComboBox addresses;
    private JButton asServer;
    private JButton asClient;
    private JXTextField serverIp;
    private ConnUtil connUtil;
    private JButton close;
    private ServerThread sThread;
    private ClientThread cThread;


    public ConnectPanel() {
        Font font = new Font("微软雅黑",Font.PLAIN,15);
        textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setMargin(new Insets(5,5,5,5));
        JScrollPane scrollPane = new JScrollPane(textArea);


        inputTxt =new JXTextField();
        inputTxt.setFont(font);
        inputTxt.setMargin(new Insets(5,5,5,5));
        inputTxt.setPrompt("挑衅一哈对手？");
        inputTxt.setPromptForeground(Color.GRAY);


        send = new JButton("发送");
        send.setFont(font);
        send.addActionListener(e->{
            textArea.append(inputTxt.getText() + "\n");
            inputTxt.setText("");
        });

        JPanel inputJP = new JPanel();
        inputJP.setLayout(new BorderLayout(5,5));
        inputJP.add(inputTxt,BorderLayout.CENTER);
        inputJP.add(send,BorderLayout.EAST);

        connUtil = new ConnUtil();
        JLabel hostTxt = new JLabel("本机地址:");
        hostTxt.setFont(font);
        addresses = new JComboBox<InetAddress>();
        addresses.addItemListener(e->{
            if(e.getStateChange() == ItemEvent.SELECTED){
                InetAddress a = (InetAddress)e.getItem();
                GameContext.setHost(a);
                textArea.append("已选择本地ip:" + a.getHostAddress() + "\n");
            }
        });
        InetAddress[] adds = connUtil.getAddress();
        for(InetAddress a : adds){
            addresses.addItem(a);
        }
        Box addBox = Box.createHorizontalBox();
        addBox.add(hostTxt);
        addBox.add(Box.createHorizontalStrut(5));
        addBox.add(addresses);

        asServer = new JButton("建立主机");
        asServer.addActionListener(e->{
            if(sThread == null) {
                sThread = new ServerThread(GameContext.getHost(),ConnectPanel.this);
                sThread.start();
            }
        });
        asClient = new JButton("连接到主机");
        asClient.addActionListener(e->{
            try {
                InetAddress remote = InetAddress.getByAddress(IPAddressUtil.textToNumericFormatV4(serverIp.getText()));
                GameContext.setRemote(remote);
                cThread = new ClientThread(GameContext.getHost(),GameContext.getRemote(),this);
                cThread.start();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }

        });
        JLabel jl = new JLabel("对方ip:");
        jl.setFont(font);
        serverIp = new JXTextField("输入对方ip地址……",Color.GRAY);
        close = new JButton("断开连接");
        close.setFont(font);
        close.addActionListener(e->{

        });
        Box connBox = Box.createHorizontalBox();
        connBox.add(asServer);
        connBox.add(Box.createHorizontalStrut(5));
        connBox.add(asClient);
        connBox.add(Box.createHorizontalStrut(5));
        connBox.add(jl);
        connBox.add(serverIp);
        connBox.add(Box.createHorizontalStrut(5));
        connBox.add(close);


        Box south = Box.createVerticalBox();
        south.add(inputJP);
        south.add(Box.createVerticalStrut(10));
        south.add(addBox);
        south.add(Box.createVerticalStrut(10));
        south.add(connBox);


        setLayout(new BorderLayout(10,10));
        add(scrollPane,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);


        setBorder(new EmptyBorder(10,10,10,10));
    }


    /**
     * 显示信息
     *
     * @param message
     */
    @Override
    public void showMessage(String message) {
        textArea.append(message + "\n");
    }
}
