package cn.ksmcbrigade.scbc.manager;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.hack.Hack;
import cn.ksmcbrigade.scbc.hack.Type;
import cn.ksmcbrigade.scbc.utils.SoundUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HackFrame {

    static {
        System.setProperty("java.awt.headless","false");
    }

   public static JTabbedPane tabbedPane;

    public static JFrame instance;

    public static boolean in = false;

    public HackFrame(){
        JFrame frame = new JFrame("SimpleClient modules list");
        frame.setSize(450, 600);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                in = false;
                instance.dispose();
                instance = null;
            }
        });

        tabbedPane = new JTabbedPane();
        frame.getContentPane().add(tabbedPane);

        for (Type type : Type.values()) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JLabel label = new JLabel(type.name());
            label.setFont(new Font("Dialog", Font.BOLD, 16));
            panel.add(label, BorderLayout.NORTH);

            DefaultListModel<Hack> listModel = new DefaultListModel<>();
            for (Hack hack : HackManager.hacks) {
                if (hack.type == type) {
                    listModel.addElement(hack);
                }
            }
            JList<Hack> list = new JList<>(listModel);
            list.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setFont(new Font("Dialog", Font.BOLD, 20));
                    return label;
                }
            });
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addMouseListener(new HackListMouseListener());
            panel.add(new JScrollPane(list), BorderLayout.CENTER);

            tabbedPane.addTab(type.name(), panel);
        }

        frame.setVisible(true);

        instance = frame;
        in = true;
    }

    class HackListMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JList<Hack> list = (JList<Hack>) e.getComponent();
            int index = list.locationToIndex(e.getPoint());
            if (index != -1) {
                Hack hack = list.getModel().getElementAt(index);
                if (hack != null) {
                    try {
                        hack.setEnabled(!hack.enabled);
                        if(hack.enabled){
                            SimpleClientBaseCore.config.logger("sound a wav.");
                            SoundUtil.playSoundFromJar("assets/ding.wav");
                        }
                        list.repaint();
                        list.clearSelection();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
