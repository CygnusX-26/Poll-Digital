import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI extends JFrame{
    JPanel jp = new JPanel();
    JLabel jl = new JLabel();
    JLabel jl2 = new JLabel();
    JLabel jl3 = new JLabel();
    JPasswordField jt = new JPasswordField("", 15);
    JButton jb = new JButton("Submit Vote");
    JButton jb2 = new JButton("Tally Votes");
    JButton jb3 = new JButton("Yes");
    JButton jb4 = new JButton("No");
    BlockChain blockChain = new BlockChain();
    int yes = 0;
    int no = 0;

    public GUI(){
        super("BlockChain");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(jp);
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jl3.setText("Pineapples belong on pizza.");
        jl3.setFont(new Font("Forte", Font.PLAIN, 40));
        jl.setText("Enter your SSN below. Don't worry, it's encrypted.");
        jl2.setText("Chain is valid Yes: " + 0 + " No: " + 0);
        jp.add(new JLabel(new ImageIcon("logo.png")));
        jp.add(jl3);
        jp.add(jl);
        jp.add(jt);
        jp.add(jb3);
        jp.add(jb4);
        jp.add(jb2);
        jp.add(jl2);
        
        jb3.addActionListener(e -> {
            String ssn = jt.getText() + ":" + "yes";
            String shaSSN = Block.applySha256(ssn.split(":")[0]);
            if (ssn.split(":")[0].equals("")){
                jl2.setText("Invalid input");
                return;
            }
            for (Block b : blockChain.getBlockChain()){
                if (b.getData().split(":")[0].equals(shaSSN)){
                    jl2.setText("You have already voted!");
                    return;
                }
            }
            jt.setText("");
            blockChain.addBlock(new Block(ssn, ""));
            if (!ssn.split(":")[0].equals("")){
                yes ++;
                jl2.setText("Thanks for voting!");
            }
            else{
                jl2.setText("Invalid input");
            }
        });
        jb4.addActionListener(e -> {
            String ssn = jt.getText() + ":" + "no";
            String shaSSN = Block.applySha256(ssn.split(":")[0]);
            if (ssn.split(":")[0].equals("")){
                jl2.setText("Invalid input");
                return;
            }
            for (Block b : blockChain.getBlockChain()){
                if (b.getData().split(":")[0].equals(shaSSN)){
                    jl2.setText("You have already voted!");
                    return;
                }
            }
            jt.setText("");
            blockChain.addBlock(new Block(ssn, ""));
            if (!ssn.split(":")[0].equals("")){
                jl2.setText("Thanks for voting!");
                no ++;
            }
            else{
                jl2.setText("Invalid input");
            }
        });
        jb2.addActionListener(e -> {
            if (blockChain.isChainValid()){
                jl2.setText("Chain is valid Yes: " + yes + " No: " + no);
            } else {
                jl2.setText("Chain is not valid!");
            }
        });
    }
}