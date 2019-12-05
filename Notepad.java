//Task reducting code
import java.awt.*; 
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.event.*;
import java.io.*;

class Notepad extends WindowAdapter
{
    JFrame f;
    JPanel p1,p2;
    JTextArea jt;
    JButton b1;
    JScrollPane sb;
    JToolBar tb;
    JMenuBar mb;
    JLabel dialogMessage;
    JMenu file,edit,view;
    JMenuItem neu,open,save,saveAs,print,exit;
    JMenuItem undo,redo,cut,copy,paste,delete,selectall,find,replace;
    JCheckBoxMenuItem bold,italic;
    JRadioButton wrap,noWrap;
    ButtonGroup btmGroup;
    Font font,fontMenu,dialogFont1,dialogFont,textFieldFont;
    JButton neuButton,openButton,saveButton,saveAsButton,
    cutButton,copyButton,pasteButton,undoButton,redoButton,
    deleteButton,saveDialog,cancelDialog,dontDialog;
    JFileChooser fc;
    File filename;
    FileInputStream fis;
    FileOutputStream fos;
    Image i;
    ImageIcon neuImage,openImage,saveImage,saveAsImage,cutImage,copyImage
    ,pasteImage,undoImage,redoImage,deleteImage,printImage,exitImage,selectAllImage,
    findImage,replaceImage,boldImage,italicImage,wrapImage;
    Dialog dialogBox;
    JDialog conformSave,find_replace;;
    int result,fn=0;
    boolean fileSaved=false,docunmentChanged=false,isUpActive=true;
    JLabel find1,replaceLabel;
    GridBagConstraints gbc;
    JButton replace1,replaceAll_1,findNext;
    JTextField replaceTextField,findTextField;
    JRadioButton up,down;
    Document doc;
    UndoManager um;

    Notepad()
    {
        f=new JFrame("Text Editor");
        jt=new JTextArea();
        jt.setCaretPosition(0);
        p1=new JPanel(new BorderLayout());   
        sb=new JScrollPane(jt);
        tb=new JToolBar();

        jt.setTabSize(4);
    //Button
        java.net.URL url;
        String loc="/Images";
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        //neuImage =new ImageIcon("E:/JavaAdv/Notepad  Version 2.0/newFileImage-32.png");
        neuImage=new ImageIcon(url);
        neuButton=new JButton(neuImage);
        neuButton.setToolTipText("New File");
        neuButton.addActionListener(e->_newFile(e));
        tb.add(neuButton);
        neuButton.setBorderPainted(false);
        neuButton.setSelected(false);

        //openImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/openFileImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        openImage=new ImageIcon(url);
        openButton=new JButton(openImage);
        openButton.setToolTipText("Open file");
        openButton.addActionListener(e->_openFile(e));
        tb.add(openButton);
        openButton.setBorderPainted(false);
        openButton.setSelected(false);

        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        saveImage=new ImageIcon(url);
        //saveImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/saveFileImage-32.png");
        saveButton=new JButton(saveImage);
        saveButton.setBorderPainted(false);
        saveButton.setToolTipText("Save File");
        saveButton.setSelected(false);
        saveButton.addActionListener(e->_saveFile());
        tb.add(saveButton);
        
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        saveAsImage=new ImageIcon(url);
        //saveAsImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/saveAsImage-32.png");
        saveAsButton=new JButton(saveAsImage);
        saveAsButton.setToolTipText("Save As");
        tb.add(saveAsButton);
        saveAsButton.addActionListener(e->_saveAsFile());
        saveAsButton.setBorderPainted(false);
        saveAsButton.setSelected(false);

        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        cutImage=new ImageIcon(url);
        //cutImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/cutImage-32.png");
        cutButton=new JButton(cutImage);
        cutButton.setToolTipText("Cut");
        tb.add(cutButton);
        cutButton.addActionListener(e->this._cut());
        cutButton.setBorderPainted(false);
        cutButton.setSelected(false);
        cutButton.setEnabled(false);

        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        copyImage=new ImageIcon(url);
        //copyImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/copyImage-32.png");
        copyButton=new JButton(copyImage);
        copyButton.setToolTipText("Copy");
        copyButton.addActionListener(e->this._copy());
        tb.add(copyButton);
        copyButton.setBorderPainted(false);
        copyButton.setSelected(false);
        copyButton.setEnabled(false);

        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        pasteImage=new ImageIcon(url);
        //pasteImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/pasteImage-32.png");
        pasteButton=new JButton(pasteImage);
        pasteButton.setToolTipText("Paste");
        pasteButton.addActionListener(e->this._paste());
        tb.add(pasteButton);
        pasteButton.setBorderPainted(false);
        pasteButton.setSelected(false);
        pasteButton.setEnabled(false);

        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        undoImage=new ImageIcon(url);
        //undoImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/undoImage-32.png");
        undoButton=new JButton(undoImage);
        undoButton.setToolTipText("Undo");
        tb.add(undoButton);
        undoButton.setBorderPainted(false);
        undoButton.setSelected(false);
        undoButton.addActionListener(e->_undo(e));
        undoButton.setEnabled(false);

        // redoImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/redoImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        redoImage=new ImageIcon(url);
        redoButton=new JButton(redoImage);
        redoButton.setToolTipText("Redo");
        tb.add(redoButton);
        redoButton.setBorderPainted(false);
        redoButton.setSelected(false);
        redoButton.setEnabled(false);
        redoButton.addActionListener(ll->_redo(ll));

        // deleteImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/deleteImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        deleteImage=new ImageIcon(url);
        deleteButton=new JButton(deleteImage);
        deleteButton.setToolTipText("Delete");
        deleteButton.addActionListener(e->this._delete());
        deleteButton.setBorderPainted(false);
        deleteButton.setSelected(false);
        tb.add(deleteButton);
        deleteButton.setEnabled(false);

        tb.setFloatable(false);        
        
    //MenuBar

        mb = new JMenuBar();

        file = new JMenu("File");
        edit = new JMenu("Edit");
        view = new JMenu("View");
        mb.add(file);
        mb.add(edit);
        mb.add(view);

        fontMenu = new Font("Arial",Font.BOLD,14);
        mb.setFont(fontMenu);

        neu=new JMenuItem("New",neuImage);
        neu.setFont(fontMenu);
        neu.addActionListener(e->_newFile(e));
        neu.setMnemonic(KeyEvent.VK_N);
        neu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));

        open=new JMenuItem("Open",openImage);
        open.addActionListener(e->_openFile(e));
        open.setFont(fontMenu);
        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));

        save=new JMenuItem("Save",saveImage);
        save.setFont(fontMenu);
        save.addActionListener(e->_saveFile());
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));

        saveAs=new JMenuItem("Save As",saveAsImage);
        saveAs.setFont(fontMenu);
        saveAs.addActionListener(e->_saveAsFile());
        saveAs.setMnemonic(KeyEvent.VK_A);
        saveAs.setDisplayedMnemonicIndex(5);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK));

        // printImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/printImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        printImage=new ImageIcon(url);
        print=new JMenuItem("Print",printImage);
        print.addActionListener(e->_print());
        print.setFont(fontMenu);
        print.setMnemonic(KeyEvent.VK_P);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK));

        // exitImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/exitImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        exitImage=new ImageIcon(url);
        exit=new JMenuItem("Exit",exitImage);
        exit.setFont(fontMenu);
        exit.setMnemonic(KeyEvent.VK_F4);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));
        exit.addActionListener(e->_exit(e));
        
        file.add(neu);
        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(print);
        file.add(exit);

        undo=new JMenuItem("Undo",undoImage);
        undo.setFont(fontMenu);
        undo.setMnemonic(KeyEvent.VK_U);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_DOWN_MASK));
        undo.addActionListener(l->_undo(l));
        undo.setEnabled(false);
        edit.add(undo);

        edit.addSeparator();
        redo=new JMenuItem("Redo",redoImage);
        redo.setFont(fontMenu);
        redo.setMnemonic(KeyEvent.VK_Z);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
        edit.add(redo);
        redo.setEnabled(false);
        

        cut=new JMenuItem("Cut",cutImage);
        cut.setFont(fontMenu);
        cut.addActionListener(e->this._cut());
        cut.setMnemonic(KeyEvent.VK_X);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK));
        cut.setEnabled(false);
        edit.add(cut);

        copy=new JMenuItem("Copy",copyImage);
        copy.setFont(fontMenu);
        copy.addActionListener(e->this._copy());
        copy.setMnemonic(KeyEvent.VK_C);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
        copy.setEnabled(false);
        edit.add(copy);

        paste=new JMenuItem("Paste",pasteImage);
        paste.setFont(fontMenu);
        paste.addActionListener(e->this._paste());
        paste.setMnemonic(KeyEvent.VK_V);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK));
        paste.setEnabled(false);
        edit.add(paste);

        edit.addSeparator();
        delete=new JMenuItem("Delete",deleteImage);
        delete.setFont(fontMenu);
        delete.addActionListener(e->this._delete());
        

        edit.add(delete);
        edit.addSeparator();

        // selectAllImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/selectAll-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        selectAllImage=new ImageIcon(url);


        selectall=new JMenuItem("Select All",selectAllImage);
        selectall.setFont(fontMenu);
        selectall.addActionListener(e->_selectAll(e));
        edit.add(selectall);

        // findImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/searchImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        findImage=new ImageIcon(url);

        
        find=new JMenuItem("Find",findImage);
        find.addActionListener(e->findReplace(e));
        find.setMnemonic(KeyEvent.VK_F);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_DOWN_MASK));

        find.setFont(fontMenu);
        edit.add(find);

        // replaceImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/findImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        replaceImage=new ImageIcon(url);

        
        replace=new JMenuItem("Replace",replaceImage);
        replace.setMnemonic(KeyEvent.VK_R);
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_DOWN_MASK));
      
        replace.setFont(fontMenu);
        edit.add(replace);

        // boldImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/boldImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        boldImage=new ImageIcon(url);

        
        bold=new JCheckBoxMenuItem("Bold",boldImage);
        bold.setFont(fontMenu);
        bold.addActionListener(e->_changeStyle());
        view.add(bold);

        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        italicImage=new ImageIcon(url);
        // italicImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/italicImage-32.png");
        italic=new JCheckBoxMenuItem("Italic",italicImage); 
        italic.setFont(fontMenu);
        italic.addActionListener(e->_changeStyle());
        view.add(italic);

        view.addSeparator();
     
        btmGroup=new ButtonGroup();

        // wrapImage=new ImageIcon("E:/JavaAdv/Notepad Version 2.0/wrapImage-32.png");
        url=this.getClass().getResource(loc+"/newFileImage-32.png");
        wrapImage=new ImageIcon(url);
       
        wrap=new JRadioButton("Wrap",false);
        view.add(wrap);

        noWrap=new JRadioButton("No Wrap",true);
        noWrap.setFont(fontMenu);
        
        btmGroup.add(wrap);
        btmGroup.add(noWrap);
        wrap.addActionListener(e->{
            jt.setLineWrap(true);
        });
        
        noWrap.addActionListener(e->jt.setLineWrap(false));
        view.add(wrap);
        view.add(noWrap);
       // Toolbar
    
        //Top menubar and toolbar setlayout
        p2=new JPanel(new GridLayout(2,1));
        p2.add(mb);
        p2.add(tb);
      
        font=new Font("Arial",Font.PLAIN,32);
        jt.setFont(font);
        p1.add(sb,BorderLayout.CENTER);
        p1.add(p2,"North");

        f.add(p1);
        f.setBounds(200,200,800,800);
        f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //Docunment event
        jt.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                fileSaved=false;
                undoButton.setEnabled(true);
                undo.setEnabled(true);
            }
            public void insertUpdate(DocumentEvent e) {
                fileSaved=false;
                undoButton.setEnabled(true);
                undo.setEnabled(true);
               
            }
            public void changedUpdate(DocumentEvent e) {
                fileSaved=false;
                undoButton.setEnabled(true);
                undo.setEnabled(true);
            }
        });
        //Caret Event
        jt.addCaretListener(l->
        {
               if((jt.getSelectionEnd()-jt.getSelectionStart())>0)
               {
                    copy.setEnabled(true);
                    cut.setEnabled(true);
                    cutButton.setEnabled(true);
                    copyButton.setEnabled(true);    
                    delete.setEnabled(true);
                    deleteButton.setEnabled(true);   
               }
               else
               {
                    copy.setEnabled(false);
                    cut.setEnabled(false);
                    cutButton.setEnabled(false);
                    copyButton.setEnabled(false);     
                    delete.setEnabled(false);
                    deleteButton.setEnabled(false);   
               }
        });
        f.setVisible(true);

        

    //find and replace box
        find_replace=new JDialog(f,false);
        find_replace.setBounds(500,500,700,300);
        find_replace.setLayout(new GridBagLayout());
        
        find1 = new JLabel("Find");
        find1.setFont(fontMenu);
        gbc=new GridBagConstraints();
        gbc.insets=new Insets(10, 10, 10, 10);
        gbc.gridx=0;
        gbc.gridy=0;
        find_replace.add(find1,gbc);
        
        findTextField=new JTextField(20);
        gbc.fill=GridBagConstraints.EAST;
        gbc.gridx=1;
        gbc.gridy=0;
        find_replace.add(findTextField,gbc);
        
        replace1=new JButton("Replace");
        gbc.gridx=0;
        gbc.gridy=4;
        find_replace.add(replace1,gbc);
        replace1.setEnabled(false);
        replace1.addActionListener(l->{

            String temp1=jt.getText();
            String temp2=findTextField.getText();
            String temp3=replaceTextField.getText();
            int k=0;
            int si,ei;
            si=temp1.indexOf(temp2,k);
            ei=si+temp2.length();
            k=si+1;
            if(si!=-1)
            {
                jt.select(si, ei);
                replace1.addActionListener(l2->jt.replaceRange(temp3,si, ei));
            }
            if(si==-1)
            {
                JDialog error=new JDialog(f,"Notepad",true);
                error.setBounds(500,500,250,200);
                JLabel msg=new JLabel("Cannot find \" "+temp2+" \"");
                JButton ok=new JButton("OK");
                ok.addActionListener(o->{
                    error.setVisible(false);
                });
                error.setLayout(new GridBagLayout());

                GridBagConstraints gbl=new GridBagConstraints();
                gbl.insets=new Insets(5,5,5,5);
                gbl.gridx=0;
                gbl.gridy=0;
                error.add(msg,gbl);
                gbl.fill=GridBagConstraints.SOUTHWEST;
                gbl.gridx=1;
                gbl.gridy=1;
                error.add(ok,gbl);
                error.setVisible(true);
                fn=0;
            }
        });

        
        replaceAll_1=new JButton("Replace All");
        gbc.gridx=1;
        gbc.gridy=4;
        find_replace.add(replaceAll_1,gbc);
        find_replace.setResizable(false);
        replaceAll_1.setEnabled(false);
        replaceAll_1.addActionListener(ll->
        {
            String temp1=jt.getText();
            String temp2=findTextField.getText();
            String temp3=replaceTextField.getText();
            int k=0;
            int si=0,ei=0;
            int no_of_items=0;  
            while(si!=-1)
            {
                si=temp1.indexOf(temp2,k);
                ei=si+temp2.length();
                k=si+1;
                if(si!=-1)
                {
                    jt.replaceRange(temp3,si, ei);
                    no_of_items++;
                }
            }
            if(no_of_items>=1)
            {
                si=0;
                JDialog error=new JDialog(f,"Notepad",true);
                error.setBounds(500,500,250,200);
                JLabel msg=new JLabel("number of item changed "+no_of_items);
                JButton ok=new JButton("OK");
                ok.addActionListener(o->{
                    error.setVisible(false);
                });
                error.setLayout(new GridBagLayout());

                GridBagConstraints gbl=new GridBagConstraints();
                gbl.insets=new Insets(5,5,5,5);
                gbl.gridx=0;
                gbl.gridy=0;
                error.add(msg,gbl);
                gbl.fill=GridBagConstraints.SOUTHWEST;
                gbl.gridx=1;
                gbl.gridy=1;
                error.add(ok,gbl);
                error.setVisible(true);
            }
            if(si==-1 )
            {
                JDialog error=new JDialog(f,"Notepad",true);
                error.setBounds(500,500,250,200);
                JLabel msg=new JLabel("Cannot find \" "+temp2+" \"");
                JButton ok=new JButton("OK");
                ok.addActionListener(o->{
                    error.setVisible(false);
                });
                error.setLayout(new GridBagLayout());

                GridBagConstraints gbl=new GridBagConstraints();
                gbl.insets=new Insets(5,5,5,5);
                gbl.gridx=0;
                gbl.gridy=0;
                error.add(msg,gbl);
                gbl.fill=GridBagConstraints.SOUTHWEST;
                gbl.gridx=1;
                gbl.gridy=1;
                error.add(ok,gbl);
                error.setVisible(true);
                fn=0;
            }
        });
        findNext=new JButton("Find Next");
        findNext.setFont(fontMenu);
        gbc.gridx=2;
        gbc.gridy=0;
        findNext.setEnabled(false);
        find_replace.add(findNext,gbc);
        
        up=new JRadioButton("up",true);
        down=new JRadioButton("down",false);
        ButtonGroup b1=new ButtonGroup();
        b1.add(up);
        b1.add(down);
        up.setFont(fontMenu);
        down.setFont(fontMenu);
        gbc.gridy=1;
        gbc.gridx=0;
        find_replace.add(up,gbc);
        gbc.gridy=1;
        gbc.gridx=1;
        find_replace.add(down,gbc);
        up.addActionListener(p->
        {
            fn=0;
            isUpActive=true;
        });
        down.addActionListener(d->
        {
            fn=jt.getText().length();
            isUpActive=false;
        });

        replaceLabel=new JLabel("Replace");
        replaceLabel.setFont(fontMenu);
        gbc.fill=GridBagConstraints.EAST;

        gbc.gridx=0;
        gbc.gridy=3;
        find_replace.add(replaceLabel,gbc);

        replaceTextField=new JTextField(20);
        gbc.gridx=1;
        gbc.gridy=3;
        find_replace.add(replaceTextField,gbc);

        
        find_replace.setResizable(false);
        find_replace.setVisible(false);


    //Docunemt event for replace text field
         replaceTextField.getDocument().addDocumentListener(new DocumentListener() {
        public void removeUpdate(DocumentEvent e) {
            if( (findTextField.getText()==null || findTextField.getText().length()==0) || (replaceTextField.getText()==null || replaceTextField.getText().length()==0))
            {
                replaceAll_1.setEnabled(false);
                replace1.setEnabled(false);
            }
            else if(findTextField.getText()==null || findTextField.getText().length()==0)
            {
                    replace1.setEnabled(false);
                    replaceAll_1.setEnabled(false);   
            }
            else
            {
                replace1.setEnabled(true);
                replaceAll_1.setEnabled(true);   
            }
        }
        public void insertUpdate(DocumentEvent e) {
            
            if( (findTextField.getText()==null || findTextField.getText().length()==0) || (replaceTextField.getText()==null || replaceTextField.getText().length()==0))
            {
                replaceAll_1.setEnabled(false);
                replace1.setEnabled(false);
            }
            else if(findTextField.getText()==null || findTextField.getText().length()==0)
            {
                    replace1.setEnabled(false);
                    replaceAll_1.setEnabled(false);   
            }
            else
            {
                replace1.setEnabled(true);
                replaceAll_1.setEnabled(true);   
            }
        }
        public void changedUpdate(DocumentEvent e) {
            
        }
        });
    findTextField.getDocument().addDocumentListener(new DocumentListener() {
        public void removeUpdate(DocumentEvent e) {
            if(findTextField.getText()==null || findTextField.getText().length()==0)
            {
                findNext.setEnabled(false);
            }
            if( (findTextField.getText()==null || findTextField.getText().length()==0) || (replaceTextField.getText()==null || replaceTextField.getText().length()==0))
            {
                replaceAll_1.setEnabled(false);
                replace1.setEnabled(false);
            }
            else if(findTextField.getText()==null || findTextField.getText().length()==0)
            {
                    replace1.setEnabled(false);
                    replaceAll_1.setEnabled(false);   
            }
            else
            {
                replace1.setEnabled(true);
                replaceAll_1.setEnabled(true);   
            }
        }
        public void insertUpdate(DocumentEvent e) {
            findNext.setEnabled(true);
            if( (findTextField.getText()==null || findTextField.getText().length()==0) || (replaceTextField.getText()==null || replaceTextField.getText().length()==0))
            {
                replaceAll_1.setEnabled(false);
                replace1.setEnabled(false);
            }
            else if(findTextField.getText()==null || findTextField.getText().length()==0)
            {
                    replace1.setEnabled(false);
                    replaceAll_1.setEnabled(false);   
            }
            else
            {
                replace1.setEnabled(true);
                replaceAll_1.setEnabled(true);   
            }
        }
        public void changedUpdate(DocumentEvent e) {}
        });

        //Registering undo/redo
        doc=jt.getDocument();
        um=new UndoManager();
        doc.addUndoableEditListener(um);

        f.addWindowListener(this);


    }
    
    public void windowClosing(WindowEvent e)
    {
            Window w=new Window(f);
            w=e.getWindow();    
            jt.selectAll();
            int n=jt.getCaretPosition();
            jt.setCaretPosition(jt.getText().length());
            if(n!=0 && fileSaved==false)
            {
                int i=_dialogBox();
                if(i==0)
                {
                    _saveFile();
                }
                else if(i==1)
                {
                    f.setVisible(false);
                    System.exit(0);
                }
            }
            else
            {
                f.setVisible(false);
                System.exit(0);
            }
    }
    private void _newFile(ActionEvent e)
    {
        jt.selectAll();
        int n=jt.getCaretPosition();
        jt.setCaretPosition(jt.getText().length());
        if(n!=0 && fileSaved==false)
        {
            int check=_dialogBox();
            if(check==0)
            {
                _saveFile();
            }
            else if(check==1)
            {
                jt.setText("");
            }
        }
        else
        {
            jt.setText("");
        }   
    }
    private void _openFile(ActionEvent e)
    {  
        //if file is not empty then it ask to save file first.
        jt.selectAll();
        int n=jt.getCaretPosition();
        jt.setCaretPosition(jt.getText().length());
        if(n!=0 && fileSaved==false)
        {
            int check=_dialogBox();
            if(check==0)
            {
                _saveFile();
            }
            else if(check==1)
            {
                _openFile(e);
            }
        }
        else
        {
            fc=new JFileChooser("C:/Users/Abhishek kapoor/Desktop/");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int responce=fc.showOpenDialog(f);
            if(responce==JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    filename = fc.getSelectedFile();
                    fis=new FileInputStream(filename);
                    try
                    {
                        byte b[]=new byte[fis.available()];
                        fis.read(b);
                        jt.setText(new String(b));   
                        fileSaved=true;
                        fis.close();
                    }
                    catch(IOException E)
                    {
                        //TODO:: open a dialog box to show the execption;
                        System.out.println(E);
                    }
                }
                catch(FileNotFoundException f)
                {
                    //TODO:: Open a dilog box for error
                    System.out.println(f);
                }
            }
        }
    }
    private void _saveFile()
    {
        if(fileSaved==false)
        {
            _saveAsFile();
        }
        else
        {
            try
            {
                fos=new FileOutputStream(filename);
                byte b[]=jt.getText().getBytes();
                fos.write(b);              
            }
            catch(IOException Ec)
            {
                System.out.println(Ec);
            }
        }
    }
    private void _saveAsFile()
    {
        fc=new JFileChooser("C:/Users/Abhishek kapoor/Desktop/");
        int x= fc.showSaveDialog(f);
        if(x==JFileChooser.APPROVE_OPTION)
        {
            try
            {
                filename=fc.getSelectedFile();
                if(filename.exists()==true)
                {
                    conformSave= new JDialog(f,"Confirm Save:",true); 
                    conformSave.setBounds(700,500,500,200);
                    conformSave.setLayout(new GridBagLayout());
                    JLabel l1,l2;
                    l1=new JLabel(filename+"");
                    l2=new JLabel("Already Exist! Do you want to replace it.");
                    JButton yes,no;
                    yes=new JButton("Yes");
                    no=new JButton("No");

                    Font font1=new Font("Arial",Font.BOLD,14);
                    l1.setFont(font1);
                    GridBagConstraints gbc;
                    gbc=new GridBagConstraints();
                    gbc.insets=new Insets(5, 5, 10, 10);
                    gbc.anchor=GridBagConstraints.WEST;
                    gbc.gridx=0;
                    gbc.gridy=0;
                    conformSave.add(l1,gbc);
                    
                    gbc.gridx=0;
                    gbc.gridy=1;
                    l2.setFont(font1);
                    conformSave.add(l2,gbc);

                    gbc.anchor=GridBagConstraints.SOUTHWEST;
                    
                    gbc.gridx=1;
                    gbc.gridy=2;
                    conformSave.add(no,gbc);

                    gbc.gridx=2;
                    gbc.gridy=2;
                    conformSave.add(yes,gbc);

                    no.addActionListener(E->{
                        conformSave.setVisible(false);
                        _saveAsFile();
                    });
                    yes.addActionListener(E->{
                        try
                        {
                            fos=new FileOutputStream(filename);
                            byte b[]=jt.getText().getBytes();
                            fos.write(b);
                            conformSave.setVisible(false);
                            fileSaved=true;                     
                        }
                        catch(IOException Ec)
                        {
                            System.out.println(Ec);
                        }
                    });
                    conformSave.setVisible(true);
                    
                }
                else
                {
                    fos=new FileOutputStream(filename);
                    byte b[]=jt.getText().getBytes();
                    fos.write(b);
                    fileSaved=true;
                }
            }
            catch(IOException E)
            {
                System.out.println(E);
            }
        }
        else if(x==JFileChooser.CANCEL_OPTION)
        {
            fc.setVisible(false);
        }
        else if(x==JFileChooser.ERROR_OPTION)
        {
            System.out.println("An error occer");
        }
    }
    
    private void _print()
    {
        try
        {
            jt.print();
        }
        catch(Exception p)
        {
            System.out.println("Printer not connected");
        }
    }
    private void _exit(ActionEvent e)
    {
        jt.selectAll();
        int n=jt.getCaretPosition();
        jt.setCaretPosition(jt.getText().length());
        if(n!=0 && fileSaved==false)
        {
            int check=_dialogBox();
            //System.out.println(check+" Check");
            if(check==0)
            {
                _saveFile();
            }
            else if(check==1)
            {
                f.setVisible(false);
                System.exit(0);
            }
        }
        else
        {
            f.setVisible(false);
            System.exit(0);
        }
    }
    //Edit options
    private void _undo(ActionEvent e)
    {
        try
        {
            redo.setEnabled(true);
            redoButton.setEnabled(true);
            um.undo();
        }
        catch(CannotUndoException Exe)
        {
            undoButton.setEnabled(false);
            undo.setEnabled(false);
        }
    }
    private void _redo(ActionEvent e)
    {
        try
        {
            um.redo();
        }
        catch(CannotRedoException Exe)
        {
            redoButton.setEnabled(false);
            redo.setEnabled(false);
        }
    }

    private void _cut()
    {
        jt.cut();
        paste.setEnabled(true);
        pasteButton.setEnabled(true);
    }
    
    private void _copy()
    {
        jt.copy();
        paste.setEnabled(true);
        pasteButton.setEnabled(true);
    }

    private void _paste()
    {
        jt.paste();
    }
    private void _selectAll(ActionEvent e)
    {
        jt.selectAll();
    }
    private void _delete()
    {
        jt.replaceSelection("");
    }
    private void _changeStyle()
    {  
        
        if(italic.getState()==true && bold.getState()==true)
        textFieldFont=new Font("Arial",Font.ITALIC | Font.BOLD,32);
    
        else if(italic.getState()==true && bold.getState()==false)
            textFieldFont=new Font("Arial",Font.ITALIC,32);

        else if(italic.getState()==false && bold.getState()==true)
            textFieldFont=new Font("Arial",Font.BOLD,32);

        else
            textFieldFont=new Font("Arial",Font.PLAIN,32);
        jt.setFont(textFieldFont);
    }
     
    private void findReplace(ActionEvent e)
    {
        findTextField.setText(jt.getSelectedText());
        jt.selectAll();
        int n=jt.getCaretPosition();
        jt.setCaretPosition(jt.getText().length());
        if(n!=0)
        {    
            find_replace.setVisible(true);
            findNext.addActionListener(mm->
            {
                if(isUpActive==true)
                {    
                    String temp1=jt.getText();
                    String temp2=findTextField.getText();

                    int si=temp1.indexOf(temp2,fn);
                    int ei=si+temp2.length();
                    fn=si+1;
                    if(si==-1)
                    {
                        JDialog error=new JDialog(f,"Notepad",true);
                        error.setBounds(500,500,250,200);
                        JLabel msg=new JLabel("Cannot find \" "+temp2+" \"");
                        JButton ok=new JButton("OK");
                        ok.addActionListener(o->{
                            error.setVisible(false);
                        });
                        error.setLayout(new GridBagLayout());

                        GridBagConstraints gbl=new GridBagConstraints();
                        gbl.insets=new Insets(5,5,5,5);
                        gbl.gridx=0;
                        gbl.gridy=0;
                        error.add(msg,gbl);
                        gbl.fill=GridBagConstraints.SOUTHWEST;
                        gbl.gridx=1;
                        gbl.gridy=1;
                        error.add(ok,gbl);
                        error.setVisible(true);
                        fn=0;
                    }
                    else
                    {
                        jt.select(si, ei);
                    }
                }
                else if (isUpActive==false)
                {
                    String temp1=jt.getText();
                    String temp2=findTextField.getText();

                    int si=temp1.lastIndexOf(temp2,fn);
                    int ei=si+temp2.length();
                    fn=si-1;
                    if(si==-1)
                    {
                        JDialog error=new JDialog(f,"Notepad",true);
                        error.setBounds(500,500,250,200);
                        JLabel msg=new JLabel("Cannot find \" "+temp2+" \"");
                        JButton ok=new JButton("OK");
                        ok.addActionListener(o->{
                            error.setVisible(false);
                        });
                        error.setLayout(new GridBagLayout());

                        GridBagConstraints gbl=new GridBagConstraints();
                        gbl.insets=new Insets(5,5,5,5);
                        gbl.gridx=0;
                        gbl.gridy=0;
                        error.add(msg,gbl);
                        gbl.fill=GridBagConstraints.SOUTHWEST;
                        gbl.gridx=1;
                        gbl.gridy=1;
                        error.add(ok,gbl);
                        error.setVisible(true);
                        fn=jt.getText().length();
                    }
                    else
                    {
                        jt.select(si, ei);
                    }

                }

            });  
        }
        else
        {
            JDialog error=new JDialog(f, "Notepad");
            error.setBounds(500,500,250,200);
            JLabel msg=new JLabel("Nothing to find !");
            JButton ok=new JButton("OK");
            ok.addActionListener(o->{
                error.setVisible(false);
            });
            error.setLayout(new GridBagLayout());

            GridBagConstraints gbl=new GridBagConstraints();
            gbl.insets=new Insets(5,5,5,5);
            gbl.gridx=0;
            gbl.gridy=0;
            error.add(msg,gbl);
            gbl.fill=GridBagConstraints.SOUTHWEST;
            gbl.gridx=1;
            gbl.gridy=1;
            error.add(ok,gbl);
            error.setVisible(true);
        }
    }


    private int _dialogBox()
    {
        //return 0-save;
        //return 1-dont save;
        //return 2-cancel;
        result=9;
        

        dialogFont=new Font("Arial",Font.BOLD,20);

        dialogBox=new Dialog(f,"Save File",true);
        dialogBox.setBounds(700,500,500,200);
        dialogBox.setLayout(new GridLayout(2, 1));
        

        dialogMessage=new JLabel("   Do you want to save changes?");
        dialogMessage.setFont(dialogFont);
        JPanel p1=new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        
        dialogFont1=new Font("Arial",Font.PLAIN,16);

        saveDialog=new JButton("Save");
        saveDialog.setFont(dialogFont1);

        saveDialog.addActionListener(e->
        {
            result=0;
            dialogBox.setVisible(false);
        });
        dontDialog=new JButton("Don't Save");
        dontDialog.setFont(dialogFont1);
       
        
        dontDialog.addActionListener(e->{
            jt.selectAll();
            jt.replaceSelection("");
            dialogBox.setVisible(false);
            result=1;
        });

        cancelDialog=new JButton("Cancel");
        cancelDialog.setFont(dialogFont1);
       
        cancelDialog.addActionListener(e->{
            dialogBox.setVisible(false);
            result=2;
        });

        p1.add(saveDialog);
        p1.add(dontDialog);
        p1.add(cancelDialog);

        dialogBox.add(dialogMessage);
        dialogBox.add(p1);
        dialogBox.addWindowListener(new WindowListener(){
            public void windowOpened(WindowEvent e){    
            }
            public void windowIconified(WindowEvent e) {    
            }
            public void windowDeiconified(WindowEvent e) {
            }        
            public void windowDeactivated(WindowEvent e) {
        
            }
            public void windowClosing(WindowEvent e) {
                dialogBox.setVisible(false);
            }
            public void windowClosed(WindowEvent e) {
            }
            public void windowActivated(WindowEvent e) {
            }
        });
        dialogBox.setVisible(true); 
        return result;
    }
    public static void main(String arg[])
    {
        Notepad n=new Notepad();
    }
}