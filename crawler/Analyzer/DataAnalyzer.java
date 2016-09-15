package Analyzer;

import Crawl.Controller;
import Crawl.Crawler;
import Crawl.IHandleGeneralListener;
import Crawl.WebSite;
import CrawlerUtil.Collectibles;
import edu.uci.ics.crawler4j.url.WebURL;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class DataAnalyzer extends JDialog {
    //models
    final DefaultListModel<WebSite> lbxVisitedModel = new DefaultListModel<>();
    DefaultListModel<WebURL> outLinkModel = new DefaultListModel<>();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList lbxVisited;
    private JButton btnViewText;
    private JLabel lblVisitedCount;
    private JLabel lblRefer;
    private JList lbxOutgoing;
    private JLabel lblOutgoing;
    private JButton btnStartCrawl;
    private JTextField a1TextField;
    private JLabel lblTries;
    private JLabel lblRejected;
    private JLabel lblVisited;
    private JLabel lblRevisited;
    private JLabel lblFiltered;
    private JLabel lblBlocked;
    private JButton btnLoadFromFile;
    private Collectibles sites = new Collectibles();
    private Controller crawlerWrapper;



    public DataAnalyzer() {
        initData();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void setLbl(JLabel lbl, int i) {
        lbl.setText(Integer.toString(i));
    }

    public static void main(String[] args) {
        DataAnalyzer dialog = new DataAnalyzer();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    void refreshData(){
        lbxVisitedModel.clear();
        lbxVisited.clearSelection();
        for(int i=0; i<sites.getVisited(); ++i)
            lbxVisitedModel.add(i,sites.get(i));
    }

    private void initData() {
        btnLoadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sites = Collectibles.load();
                refreshData();
            }
        });

        lblVisitedCount.setText(Integer.toString(sites.getVisited()));

        lbxVisited.setModel(lbxVisitedModel);
        lbxVisited.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int sel = lbxVisited.getSelectedIndex();
                if (sel != -1) {
                    lblRefer.setText(sites.get(lbxVisited.getSelectedIndex()).toString());
                    int c = 0;
                    for (WebURL w : sites.get(lbxVisited.getSelectedIndex()).getOutgoingLinks())
                        outLinkModel.add(c++, w);
                    lblOutgoing.setText(Integer.toString(sites.get(c).getOutgoingLinks().size()));
                    lbxOutgoing.setModel(outLinkModel);
                }
            }
        });

        btnStartCrawl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    crawlerWrapper = new Controller(1);
                    Crawler.addGeneralListener(new IHandleGeneralListener() {
                        @Override
                        public void Triggered() {
                            Crawler c = Crawler.getInstance();
                            setLbl(lblFiltered, c.getFiltered());
                            setLbl(lblRejected, c.getRejected());
                            setLbl(lblTries, c.getTries());
                            setLbl(lblBlocked, c.getBlocked());
                            //sites = c.getSites();
                            refreshData();
                        }
                    });
                    Thread thdCrawler = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            crawlerWrapper.begin();
                        }
                    });
                    thdCrawler.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                btnStartCrawl.setEnabled(false);
            }
        });
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

}
