import javax.swing.JFrame;

public class Frame {
    public static JFrame nouveauFrame(String titreFrame){
        JFrame frame = new JFrame(titreFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550,450);
        frame.setLocationRelativeTo(null);
        return frame;
    }
    //JFrame frameMenuResident = Frame.nouveauFrame("MaVille: Menu");
        //frameMenuResident.setVisible(true);
}