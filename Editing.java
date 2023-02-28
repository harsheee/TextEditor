//Harshi Gupta, 2010110978 , run Main.java file to run code.
//View the frame in FULL Screen /maximize the frame to view full frame
import java.awt.Font;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class Editing extends JFrame implements ActionListener {
  JTextPane textarea;
  JScrollPane sp;
  JSpinner fontsizechanger;
  JLabel sizeLabel;
  JComboBox font;
  JMenuBar menubar;
  JMenu menu;
  JTextArea findtext;
  JTextArea replacetext;
  JButton findb;
  JButton replaceb;
  JButton replaceAll;
  int pos=0;

  private JFileChooser fc = new JFileChooser();
  Editing(){
    //Making the frame
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Text Editer");
    this.setSize(500,800);
    this.setLayout(null);
    
    
    //extension 
    FileNameExtensionFilter txFilter= new FileNameExtensionFilter("Plain Text", "txt");
    fc.setFileFilter((javax.swing.filechooser.FileFilter) txFilter);
    textarea= new JTextPane();
    
    //scrollpane
    sp=new JScrollPane(textarea);
    sp.setBounds(10,150,480,400);
    sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    
    //find and replace text buttons
    findtext = new JTextArea();
    findtext.setBounds(120,40,100,25);
    findb=new JButton("FIND");
    findb.setBounds(225, 40, 100, 25);
    replacetext = new JTextArea();
    replacetext.setBounds(120,90,100,25);
    replaceb=new JButton("Replace");
    replaceb.setBounds(225, 90, 100, 25);
    replaceAll=new JButton("Replace All");
    replaceAll.setBounds(335, 90, 100, 25);

    //Text size and Font labels
    String[] allfonts= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    font = new JComboBox<>(allfonts);
    font.setBounds(120, 10, 100, 25);
    font.addActionListener(this);
    font.setSelectedItem("Calibri");


    sizeLabel = new JLabel("Size:");
    sizeLabel.setBounds(230, 10, 50, 25);
    
    fontsizechanger = new JSpinner();
    fontsizechanger.setBounds(260, 10, 50 , 25);
    
    fontsizechanger.setValue(30);
    fontsizechanger.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        textarea.setFont(new Font(textarea.getFont().getFamily(),Font.PLAIN,(int
        )fontsizechanger.getValue()));
        
      }

    });
    
    //Main Menu
    JMenuBar menubar= new JMenuBar();
    menubar.setBounds(0, 0, 500, 20);
        setJMenuBar(menubar);
        JMenu file= new JMenu("File");
        JMenu edit= new JMenu("Edit");
        JMenu review= new JMenu("Review");
        JMenu help= new JMenu("Help");
        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);
        menubar.add(review);
        pack();
        
        //Edit Menu
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem bold = new JMenuItem("Bold");
        JMenuItem italic = new JMenuItem("Italic");

        //Adding all Action Listeners
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        bold.addActionListener(this);
        italic.addActionListener(this);
        findb.addActionListener(this);
        replaceb.addActionListener(this);
        replaceAll.addActionListener(this);
        

        //add edit menu to edit
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(italic);
        edit.add(bold);
        
        
        //file opening and saving actions
        Action Open;
        Action Save;

        Open= new AbstractAction("Open File") {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                    openFile(fc.getSelectedFile().getAbsolutePath());
                }
            }

            public void openFile(String filename) {
                FileReader fr = null;
                try{
                    fr= new FileReader(filename);
                    textarea.read(fr,null);
                    fr.close();
                    setTitle(filename);
                }
                catch(IOException e){
                    System.out.println(e);
                }
            } 
        };
        Save = new AbstractAction("Save File") {
         @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();  
            }

            public void saveFile() {
                if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                    FileWriter fw =null;
                    try{
                        // JFileChooser c= new JFileChooser();
                        fw=new FileWriter(fc.getSelectedFile().getAbsolutePath()+ ".txt");
                        textarea.write(fw);
                        fw.close();
                    }
                catch(IOException e){
                    System.out.println(e);
                }
            }
        }
            
        };
    
    //adding to file menu
    file.add(Open);
    file.add(Save);

    //adding everything to main frame
    this.add(font);
    this.add(sizeLabel);
    this.add(fontsizechanger);
    this.add(sp);
    this.add(findtext);
    this.add(replacetext);
    this.add(findb);
    this.add(replaceb);
    this.add(replaceAll);
    this.setVisible(true);
    
  }

  //All events
  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()==font){
      textarea.setFont(new Font((String)font.getSelectedItem(),Font.PLAIN,textarea.getFont().getSize()));
    }

    

    // find replace and replace all
    if(e.getSource()==findb){
    String findfromtext= textarea.getText();
    String tofindtext=findtext.getText();
    int index= findfromtext.indexOf(tofindtext,pos);
    int length= tofindtext.length();
    Highlighter h=textarea.getHighlighter();
    h.removeAllHighlights();

    try{
      h.addHighlight(index, index+length, new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
    }
    catch(BadLocationException ex){

    }
    pos=index+length;
    if(pos>=findfromtext.length()){
      pos=0;
    }
    if(findfromtext.indexOf(tofindtext, pos)==-1){
      pos=0;
    }
    
  }

  if(e.getSource()==replaceb){
    String findfromtext= textarea.getText();
    String tofindtext=findtext.getText();
    String withreplacetext = replacetext.getText();
    textarea.setText(findfromtext.replaceFirst(tofindtext, withreplacetext));
    pos=0;
  }

  if(e.getSource()==replaceAll){
    String findfromtext= textarea.getText();
    String tofindtext=findtext.getText();
    String withreplacetext = replacetext.getText();
    textarea.setText(findfromtext.replaceAll(tofindtext, withreplacetext));
    pos=0;
  }

    //Bold, cut, copy , paste, italic
    String s = e.getActionCommand();
 
        if (s.equals("Bold")) {
          textarea.setFont(textarea.getFont().deriveFont(Font.BOLD));
        }
        else if (s.equals("Copy")) {
            textarea.copy();
        }

        else if (s.equals("Cut")) {
          textarea.cut();
      }
        else if (s.equals("Paste")) {
            textarea.paste();
        }
        else if (s.equals("Italic")) {
          textarea.setFont(textarea.getFont().deriveFont(Font.ITALIC));
      }

        
  }
}
