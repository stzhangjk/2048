package game.interfaces.view.mulitiPlay;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public interface IConnectView {
    /**
     * 显示信息
     * @param message
     */
    void showMessage(String message);

    /**
     * 连接时
     */
    void onConnecting();

    /**
     * 连接成功
     */
    void onSuccess();

    /**
     * 关闭连接时
     */
    void onClose();
}
