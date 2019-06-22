/**************************************
**  Project: Text Editor
**  ProjectName: WordIt
**  Course: CSD207
**  Instructor: Debopam Acharya, Sonia Khetarpaul
**  Made by:
*               SOMYA SRIVASTAVA
*               SS718
*               1710110338
* 
*               SIDDHARTH SINGH
*               SS149
*               1710110331
* 
**  
**************************************/


//All Imports
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.text.*;

//Main Class
public class WordIt extends JFrame implements ActionListener,MouseListener
{   //Declaring Main Elements of Editor
    String selectedText;
    String fileName;
    JTextPane editor;
    JFileChooser fileChooser;
    JLabel countLabel;
    int charCount;
    int wordCount;
    
    //Constructor
    public WordIt() 
    {        
        //Setting up User Interface
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(WordIt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(WordIt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(WordIt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(WordIt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Initialising Data
        selectedText = "";
        fileName = "c:\\Users";
        fileChooser = new JFileChooser(fileName);
        charCount = 0;
        wordCount = 0;
        setTitle("WordIt");
        setLayout(new BorderLayout());
        
        //Creating MenuBar
        JMenuBar menuBar = new JMenuBar();
        
        //Declaring and Adding Elements to Menubar
        JMenu fileMenu = new JMenu("File");     //Menu for New, Open, Save
        JMenu editMenu = new JMenu("Edit");     //Menu for Cut, Copy, Paste
        JMenuItem find = new JMenuItem("Search");   //Find and Replace
        JMenu Mform = new JMenu("Format");      //Menu for Font, Size, Case
        JMenu shapes = new JMenu("Shapes");       //Menu for Shapes
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(find);
        menuBar.add(Mform);
        menuBar.add(shapes);
        
        //Adding Action Listener for Search
        find.addActionListener(this);
        
        //Setting Dimensions
        Dimension dFormat = new Dimension(Mform.getPreferredSize().width,menuBar.getMaximumSize().height);
        Dimension dShape = new Dimension(shapes.getPreferredSize().width,menuBar.getMaximumSize().height);
        Dimension dFind = new Dimension(find.getPreferredSize().width,menuBar.getMaximumSize().height);
        Mform.setMaximumSize(dFormat);
        shapes.setMaximumSize(dShape);
        shapes.setMaximumSize(dFind);
        
        
        //Creating & Adding Menu Items under File Menu
        JMenuItem   newButton = new JMenuItem("New");   //New
        JMenuItem   openButton = new JMenuItem("Open"); //Open
        JMenuItem   saveButton = new JMenuItem("Save"); //Save
        
        fileMenu.add(newButton);
        fileMenu.add(openButton);
        fileMenu.add(saveButton);
        
        newButton.addActionListener(this);
        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        
        
        //Creating & Adding Menu Items under Edit Menu
        JMenuItem   copyButton = new JMenuItem("Copy (Ctrl + C)");      //Copy
        JMenuItem   cutButton = new JMenuItem("Cut (Ctrl + X)");        //Cut
        JMenuItem   pasteButton = new JMenuItem("Paste (Ctrl + V)");    //Paste
        
        editMenu.add(copyButton);
        editMenu.add(cutButton);
        editMenu.add(pasteButton);
        
        copyButton.addActionListener(this); 
        cutButton.addActionListener(this);
        pasteButton.addActionListener(this);
        
        
        //Creating & Adding Elements under Format Menu
        JMenuItem font = new JMenuItem("Font & Size");    //Font & Size
        JMenu Case = new JMenu("Case");                     //Menu for Uppercase and Lowercase
        JMenuItem uppercase = new JMenuItem("Uppercase"); //Uppercase
        JMenuItem lowercase = new JMenuItem("Lowercase"); //Lowercase
        
        Case.add(uppercase);    //adding Uppercase to Case
        Case.add(lowercase);    //adding Lowercase to Case
        Mform.add(font);
        Mform.add(Case);
        
        font.addActionListener(this);
        uppercase.addActionListener(this);
        lowercase.addActionListener(this);
        
        
        //Creating & Adding Elements under Shapes Menu
        JMenuItem Arc = new JMenuItem("Arc");
        JMenuItem Square = new JMenuItem("Square");
        JMenuItem roundRectangle = new JMenuItem("Round Rectangle");
        JMenuItem Circle = new JMenuItem("Circle");
        JMenuItem Rectangle = new JMenuItem("Rectangle");
        
        shapes.add(Arc);
        shapes.add(Square);
        shapes.add(roundRectangle);
        shapes.add(Circle);
        shapes.add(Rectangle);
        
        Arc.addActionListener(this);
        Square.addActionListener(this);
        roundRectangle.addActionListener(this);
        Circle.addActionListener(this);
        Rectangle.addActionListener(this);
        
        
        //Creating Menu for Word Count and Charecter Count
        countLabel = new JLabel("Selected Words = "+wordCount+" Selected Characters = "+charCount);
        add(countLabel,BorderLayout.SOUTH);        
        add(menuBar,BorderLayout.NORTH);
        
        
        //Starting Editor
        editor = new JTextPane();
        editor.addMouseListener(this);
        
        
        //Scrolling Option
        JScrollPane scrollPane = new JScrollPane(editor);
        add(scrollPane);
        pack();       
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        //Calling Function Based on Option Chosen by User
        switch(e.getActionCommand())
        {
            case "New":     editor.setText("");
                            break;
            case "Open":    if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                            {
                                fileName = fileChooser.getSelectedFile().getAbsolutePath();
                                try{
                                    FileReader f = new FileReader(fileName);
                                    editor.read(f, null);
                                    f.close();
                                }  
                                catch (IOException ex){
                                    JOptionPane.showMessageDialog(null, "File not found");
                                }
                            
                            }
                            break;
            case "Save":    if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                            {   
                                fileName = fileChooser.getSelectedFile().getAbsolutePath();
                                File f1 = new File(fileName);
                                if(f1.exists()){
                                }
                                else
                                    try{
                                    f1.createNewFile();
                                    } 
                                    catch (IOException ex){
                                        JOptionPane.showMessageDialog(null, "Unexpected Error Occured");
                                    }
                            
                                    try{
                                        FileWriter f = new FileWriter(fileName);
                                
                                    editor.write(f);
                                    } 
                                    catch (IOException ex) {
                                    }
                            
                            }
                            break;
            case "Copy (Ctrl + C)": editor.copy();
                                    break;
            case "Cut (Ctrl + X)":  editor.cut();
                                    break;
            case "Paste(Ctrl + V)": editor.paste();
                                    break;
            case "Search":  Search searchObject = new Search(editor);
                            break;
            case "Font & Size": Format y = new Format();
                                break;
            case "Uppercase":   Upper u = new Upper(editor);
                                break;
            case "Lowercase":   Lower l = new Lower(editor);
                                break;
            case "Arc": JFrame frameArc = new JFrame("Arc");
                        frameArc.add(new ResizeArc());
                        frameArc.setSize(300, 300);
                        frameArc.setLocationRelativeTo(null);
                        frameArc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frameArc.setVisible(true);
                        break;
            case "Square":  JFrame frameSquare = new JFrame("Square");
                            frameSquare.add(new ResizeSquare());
                            frameSquare.setSize(300, 300);
                            frameSquare.setLocationRelativeTo(null);
                            frameSquare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            frameSquare.setVisible(true);
                            break;
            case "Round Rectangle": JFrame frameRR = new JFrame("Round Rectangle");
                                    frameRR.add(new ResizeRound());
                                    frameRR.setSize(300, 300);
                                    frameRR.setLocationRelativeTo(null);
                                    frameRR.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    frameRR.setVisible(true);
                                    break;
            case "Circle":  JFrame frameCircle = new JFrame("Circle");
                            frameCircle.add(new ResizeCircle());
                            frameCircle.setSize(300, 300);
                            frameCircle.setLocationRelativeTo(null);
                            frameCircle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            frameCircle.setVisible(true);
                            break;
            case "Rectangle":   JFrame frameRectangle = new JFrame("Rectangle");
                                frameRectangle.add(new ResizeRectangle());
                                frameRectangle.setSize(300, 300);
                                frameRectangle.setLocationRelativeTo(null);
                                frameRectangle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                frameRectangle.setVisible(true);
                                break;
        }
    }
    
    //Main Function
    public static void main(String[] args)   
    {   WordIt NEW = new WordIt();
                NEW.setVisible(true);
                NEW.setSize(700,700);
                NEW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                NEW.setLocationRelativeTo(null);
    }

   
    public void mouseClicked(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e) {}
    
    //Triggers Word and Charecter counting
    public void mouseReleased(MouseEvent e) 
    {
        wordCount = 0;
        charCount = 0;
        count();
        countLabel.setText("Selected Words = "+wordCount+" Selected Characters = "+charCount);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    //Method to count Number of Words and Charecters
    public void count(){
        LinkedList list = new LinkedList();
        try
        {
            //Exceptions remover
            StringTokenizer string = new StringTokenizer(editor.getSelectedText(),"\\.|,| |;|:|\\?|\\(|\\)|\\'");
            
            //Checking for remaining Elements
            while(string.hasMoreElements()){
            list.add(string.nextToken());
            }
            
            //Word Count
            wordCount = list.size();
            
            //Charecter Count
            for(int i = 0;i<list.size();i++){
            charCount += list.get(i).toString().length();
            }
        }
        catch(NullPointerException e)
        {
            wordCount = 0;
            charCount = 0;
        }               
    }           
}

//Class for Find, Replace and ReplaceAll option
class Search extends JFrame implements ActionListener
{   
    //Creating new Elements for New Panel
    JTextPane editor;
    JTextField  findField;
    JTextField  replaceField;
    int curIndex;
    
    //Constructor
    public Search(JTextPane editorPane)
    {   
        //Creating Main Elements
        curIndex = 0;
        editor = editorPane;
        
        //Layout
        setLayout(new BorderLayout(3,3));
        
        //Creating New Panels
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3= new JPanel();
        
        //Layout of Panels
        p1.setLayout(new GridLayout(3,1));
        p2.setLayout(new GridLayout(3,1));
        p3.setLayout(new GridLayout(3,1));
        
        //Creating Labels
        JLabel l1 = new JLabel("Find: ");
        JLabel l2 = new JLabel("Replace: ");
        findField = new JTextField();
        replaceField = new JTextField();
        
        //Creating Buttons to Find, Replace and Replace All
        JButton b1 = new JButton("Find Next");
        JButton b2 = new JButton("Replace");
        JButton b3 = new JButton("Replace All");
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        
        //Adding Labels and Buttons
        p1.add(l1);
        p1.add(l2);
        add(p1,BorderLayout.WEST);
        p2.add(findField);
        p2.add(replaceField);
        add(p2,BorderLayout.CENTER);
        p3.add(b1);
        p3.add(b2);
        p3.add(b3);
        add(p3,BorderLayout.EAST);//adding components to frame
                    
        setVisible(true);
        setLocationRelativeTo(editor);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,150);
                    
    }
    
    //Function to Find
    public void findNext(){
        
        curIndex = editor.getText().toLowerCase().replaceAll("[\\n\\t]","").indexOf(findField.getText().toLowerCase(), curIndex);
        
        //In case of Empty
        if(curIndex==-1){
            JOptionPane.showMessageDialog(null, "Could not find \""+findField.getText()+"\"");
            curIndex = 0;
        }
        //Finding Given Text
        else{
            editor.select(curIndex, curIndex+findField.getText().length());
            curIndex+=findField.getText().length();
        }
    }
    
    //Function for Replace
    public void replace()
    {   
        //In case Selected Text is empty
        if(editor.getSelectedText()==null){
            findNext();
        }
        
        //Replacing
        if(editor.getSelectedText()!=null){
            editor.replaceSelection(replaceField.getText());
            curIndex=curIndex-findField.getText().length()+replaceField.getText().length();
        }
    }
    
    //Function to Replace All
    public void replaceAll(){
        
        String editorText = editor.getText().toLowerCase().replaceAll("[\\n\\t]","");
        if(editorText.contains(findField.getText().toLowerCase())==false){
            JOptionPane.showMessageDialog(null, "Could not find \""+findField.getText()+"\"");
        }
        editorText = editorText.replace(findField.getText().toLowerCase(), replaceField.getText());
        editor.setText(editorText);
    }
    
    //Action
    public void actionPerformed(ActionEvent e) 
    {
        switch(e.getActionCommand())
        {
            case "Find Next":   findNext();
                                break;
            case "Replace":     replace();
                                break;
            case "Replace All": replaceAll();
                                break;
        }
    }
}

//Upper Case Class
class Upper{
    public Upper(JTextPane editorPane){
        
        //Finding Start and Finish
        int start = editorPane.getSelectionStart();
        int end = editorPane.getSelectionEnd();
        
        //Getting Text to Change
        String texttoupdate = editorPane.getText();
        
        //Updating Text to UpperCase
        String before = texttoupdate.substring(start, end);
        String after = before.toUpperCase();
        String updated = texttoupdate.substring(0,start)+after+texttoupdate.substring(start+after.length());
        
        //Sending Back Updated Text
        editorPane.setText(updated);
    }    
}

//Lower Case Class
class Lower{
    public Lower(JTextPane editorPane){
        
        //Finding Start and End
        int start = editorPane.getSelectionStart();
        int end = editorPane.getSelectionEnd();
        
        //Getting Text to Change
        String texttoupdate = editorPane.getText();
        
        //Updating Text to LowerCase
        String before = texttoupdate.substring(start, end);
        String after = before.toLowerCase();
        String update = texttoupdate.substring(0,start)+after+texttoupdate.substring(start+after.length());
        
        //Sending Back Updated Text
        editorPane.setText(update);
    }
}

//Font & Size
class Format extends JFrame{
    JComboBox font;
    JComboBox size;
    JComboBox Case;
    
    //Main Constructor
    public  Format()
    {
        
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String[] sizes={"1","2","4","6","8","10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40","42","44","46","48","50"}; 
        
        font=new JComboBox(fonts);
        size=new JComboBox(sizes);
        
        font.setEditable(false);
        size.setEditable(false);
        
        //Font Change
        font.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                String fontchange=(String) font.getSelectedItem();
                font.setAction(new StyledEditorKit.FontFamilyAction(fontchange,fontchange));
                
            }
        });
        
        //SizeChange
        size.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                String sizestring=(String) size.getSelectedItem();
                int sizechange=Integer.parseInt(sizestring);
                size.setAction(new StyledEditorKit.FontSizeAction(sizestring,sizechange));
            }
        });
        
        //Display
        JLabel f=new JLabel("Font: ");
        JLabel s=new JLabel("Size: ");
        
        setLayout(new GridLayout(2,2));
        add(f);
        add(font);
        add(s);
        add(size);
        setSize(200,100);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}

//Arc
class ResizeArc extends JPanel{
    private int SIZE = 8;
    private Arc2D[] points = {
        new Arc2D.Double(50, 100,0, 120,0,0,Arc2D.PIE), 
        new Arc2D.Double(150, 100,100, 100,50,50,Arc2D.PIE) 
    };
  
    Arc2D s = new Arc2D.Double();
    ShapeResizeHandler ada = new ShapeResizeHandler();

    public ResizeArc() {
               
        addMouseListener(ada);
        addMouseMotionListener(ada);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for(int i = 0; i < points.length; i++) {
            g2.fill(points[i]);
        }
    
        s.setAngles(points[0].getCenterX(), points[0].getCenterY(),
        Math.abs(points[1].getCenterX()-points[0].getCenterX()),
        Math.abs(points[1].getCenterY()- points[0].getCenterY()));

        g2.draw(s);
    }

    class ShapeResizeHandler extends MouseAdapter {
        Arc2D r = new Arc2D.Double(0,0,SIZE,SIZE,0,0,0);
        private int pos = -1;
        public void mousePressed(MouseEvent event) {
            Point p = event.getPoint();

            for (int i = 0; i < points.length; i++) {
                if (points[i].contains(p)) {
                    pos = i;
                    return;
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            pos = -1;
        }

        public void mouseDragged(MouseEvent event) {
            if(pos == -1)
            return;

            points[pos].setAngles(event.getPoint().x,event.getPoint().y,points[pos].getWidth(),points[pos].getHeight());
            repaint();
        }
    }
}

//Round Rectangle
class ResizeRound extends JPanel {
    private int SIZE = 8;
    private RoundRectangle2D[] points = {
        new RoundRectangle2D.Double(50, 50,SIZE, SIZE,30,30),
        new RoundRectangle2D.Double(150, 100,SIZE, SIZE,20,20)
    };
    
    RoundRectangle2D s = new RoundRectangle2D.Double();
    ShapeResizeHandler ada = new ShapeResizeHandler();

    public ResizeRound() {
        addMouseListener(ada);
        addMouseMotionListener(ada);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        Graphics2D g2 = (Graphics2D) g;

        for(int i = 0; i < points.length; i++) {
            g2.fill(points[i]);
        }
        
        s.setRoundRect(points[0].getCenterX(), points[0].getCenterY(),
        Math.abs(points[1].getCenterX()-points[0].getCenterX()),
        Math.abs(points[1].getCenterY()- points[0].getCenterY()),45,45);

        g2.draw(s);
    }

    class ShapeResizeHandler extends MouseAdapter {
        RoundRectangle2D r = new RoundRectangle2D.Double(0,0,SIZE,SIZE,10,10);
        private int pos = -1;
        public void mousePressed(MouseEvent event) {
            Point p = event.getPoint();

            for(int i = 0; i < points.length; i++) {
                if (points[i].contains(p)) {
                    pos = i;
                    return;
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            pos = -1;
        }

        public void mouseDragged(MouseEvent event) {
            if (pos == -1)
            return;

            points[pos].setFrame(event.getPoint().x,event.getPoint().y,points[pos].getWidth(),
            points[pos].getHeight());
            repaint();
        }
    }
}

//Sqaure
class ResizeSquare extends JPanel {
    private int SIZE = 8;
    private Rectangle2D[] points = { 
        new Rectangle2D.Double(50, 50,SIZE, SIZE),
        new Rectangle2D.Double(150, 150,SIZE, SIZE)
    };
    
    Rectangle2D s = new Rectangle2D.Double();
    ShapeResizeHandler ada = new ShapeResizeHandler();

    public ResizeSquare() {
        addMouseListener(ada);
        addMouseMotionListener(ada);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for(int i = 0; i < points.length; i++) {
            g2.fill(points[i]);
        }
        s.setRect(points[0].getCenterX(), points[0].getCenterX(),
        Math.abs(points[1].getCenterX()-points[0].getCenterX()),
        Math.abs(points[1].getCenterY()- points[0].getCenterY()));

        g2.draw(s);
    }

    class ShapeResizeHandler extends MouseAdapter {
        Rectangle2D r = new Rectangle2D.Double(0,0,SIZE,SIZE);
        private int pos = -1;
        public void mousePressed(MouseEvent event) {
            Point p = event.getPoint();

            for(int i = 0; i < points.length; i++) {
                if (points[i].contains(p)) {
                    pos = i;
                    return;
                }
            }
        }

    public void mouseReleased(MouseEvent event) {
        pos = -1;
    }

    public void mouseDragged(MouseEvent event) {
        if (pos == -1)
            return;

        points[pos].setRect(event.getPoint().x,event.getPoint().y,points[pos].getWidth(),
        points[pos].getHeight());
        repaint();
    }
  }
}

//Circle
class ResizeCircle extends JPanel {
    private int SIZE = 8;
    private Ellipse2D[] points = {
        new Ellipse2D.Double(50, 50,SIZE, SIZE),
        new Ellipse2D.Double(150, 100,SIZE, SIZE)
    };
    
    Ellipse2D s = new Ellipse2D.Double();
    ShapeResizeHandler ada = new ShapeResizeHandler();

    public ResizeCircle() {
        addMouseListener(ada);
        addMouseMotionListener(ada);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for(int i = 0; i < points.length; i++) {
            g2.fill(points[i]);
        }
        s.setFrame(points[0].getCenterX(), points[0].getCenterY(),
        Math.abs(points[1].getCenterX()-points[0].getCenterX()),
        Math.abs(points[1].getCenterY()- points[0].getCenterY()));

        g2.draw(s);
    }

    class ShapeResizeHandler extends MouseAdapter {
        Ellipse2D r = new Ellipse2D.Double(0,0,SIZE,SIZE);
        private int pos=-1;
        public void mousePressed(MouseEvent event) {
            Point p = event.getPoint();
      
            for(int i = 0; i < points.length; i++) {
                if (points[i].contains(p)) {
                    pos = i;
                    return;
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            pos = -1;
        }

        public void mouseDragged(MouseEvent event) {
            if (pos == -1)
                return;

            points[pos].setFrame(event.getPoint().x,event.getPoint().y,points[pos].getWidth(),
            points[pos].getHeight());
            repaint();
        }
    }
}

//Rectangle
class ResizeRectangle extends JPanel {
    private int SIZE = 8;
    private Rectangle2D[] points = {
        new Rectangle2D.Double(50, 50,SIZE, SIZE),
        new Rectangle2D.Double(150, 100,SIZE, SIZE)
    };
    
    Rectangle2D s = new Rectangle2D.Double();

    ShapeResizeHandler ada = new ShapeResizeHandler();

    public ResizeRectangle() {
        addMouseListener(ada);
        addMouseMotionListener(ada);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for(int i = 0; i < points.length; i++) {
            g2.fill(points[i]);
        }
        s.setRect(points[0].getCenterX(), points[0].getCenterY(),
        Math.abs(points[1].getCenterX()-points[0].getCenterX()),
        Math.abs(points[1].getCenterY()- points[0].getCenterY()));

        g2.draw(s);
    }

    class ShapeResizeHandler extends MouseAdapter {
        Rectangle2D r = new Rectangle2D.Double(0,0,SIZE,SIZE);
        private int pos = -1;
        public void mousePressed(MouseEvent event) {
            Point p = event.getPoint();

            for(int i = 0; i < points.length; i++) {
                if (points[i].contains(p)) {
                    pos = i;
                    return;
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            pos = -1;
        }

        public void mouseDragged(MouseEvent event) {
            if (pos == -1)
                return;

            points[pos].setRect(event.getPoint().x,event.getPoint().y,points[pos].getWidth(),
            points[pos].getHeight());
            repaint();
        }
    }
}
