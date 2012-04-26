/*
 * Copyright (C) 2008-12  Bernhard Hobiger
 *
 * This file is part of HoDoKu.
 *
 * HoDoKu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HoDoKu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HoDoKu. If not, see <http://www.gnu.org/licenses/>.
 */

package sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.text.MessageFormat;
import java.util.Enumeration;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author  hobiwan
 */
public class ConfigSolverPanel extends javax.swing.JPanel
implements ListDragAndDropChange {
    private static final long serialVersionUID = 1L;
    private StepConfig[] steps;
    private DefaultListModel model;
    private int dropIndex = -1;
    private StepConfig dropObject;
    private Color dndColor;
    private Stroke dndStroke;
    
    // wird true gesetzt, nachdem ein Eintrag selektiert wurde; beim
    // nächsten Klick wird dann getoggelt
    private boolean firstSelected = false;
    
    private boolean listView = false; // absichtlich verkehrt, damit stepList gesetzt wird
    
    /** Creates new form ConfigSolverPanel */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public ConfigSolverPanel() {
        initComponents();
        
        Color tmpColor = UIManager.getColor("List.foreground");
        dndColor = new Color(tmpColor.getRed(), tmpColor.getGreen(), tmpColor.getBlue(), 100);
        dndStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        
        stepList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stepList.setCellRenderer(new CheckBoxRenderer());
        model = new DefaultListModel();
        stepList.setModel(model);
        new ListDragAndDrop(stepList, this, this);
        
        stepTree.setCellRenderer(new CheckRenderer());
        stepTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        stepTree.putClientProperty("JTree.lineStyle", "Angled");
        
        for (int i = 1; i < Options.getInstance().getDifficultyLevels().length; i++) {
            levelComboBox.addItem(Options.getInstance().getDifficultyLevels()[i].getName());
        }
        
        NumbersOnlyDocument doc = new NumbersOnlyDocument();
        doc.addDocumentListener(new MyDocumentListener());
        scoreTextField.setDocument(doc);
        
        // Alle Werte aus den Default-Optionen setzen
        initAll(false);
        
        checkButtons(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stepTree = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        levelLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        levelComboBox = new javax.swing.JComboBox();
        scoreTextField = new javax.swing.JTextField();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        stepScrollPane = new javax.swing.JScrollPane();
        stepList = new javax.swing.JList();
        jToolBar1 = new javax.swing.JToolBar();
        listButton = new javax.swing.JToggleButton();
        treeButton = new javax.swing.JToggleButton();

        stepTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                stepTreeMousePressed(evt);
            }
        });
        stepTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                stepTreeValueChanged(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel"); // NOI18N
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ConfigSolverPanel.jPanel3.border.title"))); // NOI18N

        levelLabel.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel").getString("ConfigSolverPanel.levelLabel.mnemonic").charAt(0));
        levelLabel.setLabelFor(levelComboBox);
        levelLabel.setText(bundle.getString("ConfigSolverPanel.levelLabel.text")); // NOI18N

        scoreLabel.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel").getString("ConfigSolverPanel.scoreLabel.mnemonic").charAt(0));
        scoreLabel.setLabelFor(scoreTextField);
        scoreLabel.setText(bundle.getString("ConfigSolverPanel.scoreLabel.text")); // NOI18N

        levelComboBox.setEnabled(false);
        levelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelComboBoxActionPerformed(evt);
            }
        });

        scoreTextField.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(levelLabel)
                    .addComponent(scoreLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scoreTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(levelComboBox, 0, 224, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelLabel)
                    .addComponent(levelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scoreLabel)
                    .addComponent(scoreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        upButton.setMnemonic(java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel").getString("ConfigSolverPanel.upButton.mnemonic").charAt(0));
        upButton.setText(bundle.getString("ConfigSolverPanel.upButton.text")); // NOI18N
        upButton.setEnabled(false);
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        downButton.setMnemonic(java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel").getString("ConfigSolverPanel.downButton.mnemonic").charAt(0));
        downButton.setText(bundle.getString("ConfigSolverPanel.downButton.text")); // NOI18N
        downButton.setEnabled(false);
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        resetButton.setMnemonic(java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel").getString("ConfigSolverPanel.resetButton.mnemonic").charAt(0));
        resetButton.setText(bundle.getString("ConfigSolverPanel.resetButton.text")); // NOI18N
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(downButton)
                        .addGap(105, 105, 105)
                        .addComponent(resetButton))
                    .addComponent(upButton)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {downButton, upButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                .addComponent(upButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(downButton)
                    .addComponent(resetButton)))
        );

        jPanel4.setLayout(new java.awt.BorderLayout());

        stepList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stepListMouseClicked(evt);
            }
        });
        stepList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                stepListValueChanged(evt);
            }
        });
        stepScrollPane.setViewportView(stepList);

        jPanel4.add(stepScrollPane, java.awt.BorderLayout.CENTER);

        listButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/listview16b.png"))); // NOI18N
        listButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(listButton);

        treeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/treeview16b.png"))); // NOI18N
        treeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(treeButton);

        jPanel4.add(jToolBar1, java.awt.BorderLayout.NORTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void stepTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stepTreeMousePressed
        TreePath path = stepTree.getPathForLocation(evt.getX(), evt.getY());
        if (path == null) {
            return;
        }
        CheckNode act = (CheckNode)path.getLastPathComponent();
        CheckNode last = (CheckNode)stepTree.getLastSelectedPathComponent();
        if (act != null && last != null && act == last) {
            last.toggleSelectionState();
            stepTree.repaint();
        }
    }//GEN-LAST:event_stepTreeMousePressed
    
    private void stepTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_stepTreeValueChanged
//        CheckNode last = (CheckNode)stepTree.getLastSelectedPathComponent();
//        last.toggleSelectionState();
//        stepTree.repaint();
    }//GEN-LAST:event_stepTreeValueChanged
    
    private void treeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treeButtonActionPerformed
        checkButtons(false);
    }//GEN-LAST:event_treeButtonActionPerformed
    
    private void listButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listButtonActionPerformed
        checkButtons(true);
    }//GEN-LAST:event_listButtonActionPerformed
    
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        initAll(true);
    }//GEN-LAST:event_resetButtonActionPerformed
    
    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
        int index = stepList.getSelectedIndex();
        if (index < steps.length - 1) {
            moveOneStep(index, true);
//            StepConfig dummy = steps[index];
//            steps[index] = steps[index + 1];
//            steps[index + 1] = dummy;
//            int dummyIndex = steps[index].getIndex();
//            steps[index].setIndex(steps[index + 1].getIndex());
//            steps[index + 1].setIndex(dummyIndex);
//            model.remove(index);
//            model.add(index + 1, steps[index + 1]);
//            stepList.setSelectedIndex(index + 1);
//            stepList.ensureIndexIsVisible(index + 1);
//            stepList.repaint();
        }
    }//GEN-LAST:event_downButtonActionPerformed
    
    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        int index = stepList.getSelectedIndex();
        if (index > 0) {
            moveOneStep(index, false);
//            StepConfig dummy = steps[index];
//            steps[index] = steps[index - 1];
//            steps[index - 1] = dummy;
//            int dummyIndex = steps[index].getIndex();
//            steps[index].setIndex(steps[index - 1].getIndex());
//            steps[index - 1].setIndex(dummyIndex);
//            model.remove(index);
//            model.add(index - 1, steps[index - 1]);
//            stepList.setSelectedIndex(index - 1);
//            stepList.ensureIndexIsVisible(index - 1);
//            stepList.repaint();
        }
    }//GEN-LAST:event_upButtonActionPerformed
    
    private void levelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelComboBoxActionPerformed
        int index = stepList.getSelectedIndex();
        if (index != -1) {
            StepConfig conf = (StepConfig)stepList.getSelectedValue();
            conf.setLevel(levelComboBox.getSelectedIndex() + 1);
        }
    }//GEN-LAST:event_levelComboBoxActionPerformed
    
    private void stepListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_stepListValueChanged
        if (evt == null || ! evt.getValueIsAdjusting()) {
            if (stepList.getSelectedValue() == null) {
                return;
            }
            firstSelected = true;
            levelComboBox.setEnabled(true);
            scoreTextField.setEnabled(true);
            StepConfig conf = (StepConfig)stepList.getSelectedValue();
            levelComboBox.setSelectedIndex(conf.getLevel() - 1);
            scoreTextField.setText(Integer.toString(conf.getBaseScore()));
            // "Nach oben" und "Nach unten" Buttons anpassen
            upButton.setEnabled(true);
            downButton.setEnabled(true);
            if (stepList.getSelectedIndex() == 0) {
                upButton.setEnabled(false);
            }
            if (stepList.getSelectedIndex() >= steps.length - 1) {
                downButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_stepListValueChanged
    
    private void stepListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stepListMouseClicked
        if (firstSelected) {
            firstSelected = false;
        } else {
            int index = stepList.locationToIndex(evt.getPoint());
            if (index == stepList.getSelectedIndex()) {
                StepConfig conf = (StepConfig)stepList.getSelectedValue();
                conf.setEnabled(! conf.isEnabled());
                stepList.repaint();
            }
        }
    }//GEN-LAST:event_stepListMouseClicked
    
    private void moveOneStep(int index, boolean up) {
        //System.out.println("move one step: " + index + "/" + up);
        int toIndex = up ? index + 1 : index - 1;
        StepConfig dummy = steps[index];
        steps[index] = steps[toIndex];
        steps[toIndex] = dummy;
        int dummyIndex = steps[index].getIndex();
        steps[index].setIndex(steps[toIndex].getIndex());
        steps[toIndex].setIndex(dummyIndex);
        model.remove(index);
        model.add(toIndex, steps[toIndex]);
        stepList.setSelectedIndex(toIndex);
        stepList.ensureIndexIsVisible(toIndex);
        stepList.repaint();
    }
    
    @Override
    public void moveStep(int fromIndex, int toIndex) {
        //System.out.println("moving: " + fromIndex + "/" + toIndex);
        boolean up = fromIndex < toIndex ? true : false;
        int anz = Math.abs(fromIndex - toIndex);
        if (up) {
            anz--;
        }
        for (int i = 0; i < anz; i++) {
            moveOneStep(fromIndex, up);
            if (up) {
                fromIndex++;
            } else {
                fromIndex--;
            }
        }
    }

    @Override
    public void setDropLocation(int index, StepConfig object) {
        dropIndex = index;
        dropObject = object;
        if (index != -1) {
            if (index <= stepList.getFirstVisibleIndex() + 1) {
                stepList.ensureIndexIsVisible(index - 1);
            } else if (index >= stepList.getLastVisibleIndex() - 1) {
                stepList.ensureIndexIsVisible(index + 1);
            }
        }
    }
    
    public void okPressed() {
        // Alle Werte übernehmen
        Options.getInstance().solverSteps = Options.getInstance().copyStepConfigs(steps, false, true);
        Options.getInstance().adjustOrgSolverSteps();
    }
    
    private void initAll(boolean setDefault) {
        // Zuerst die Daten zurücksetzen
        if (setDefault) {
            steps = Options.getInstance().copyStepConfigs(Options.DEFAULT_SOLVER_STEPS, true, false);
        } else {
            steps = Options.getInstance().copyStepConfigs(Options.getInstance().solverSteps, true, false);
        }
        
        // Liste neu laden
        model.removeAllElements();
        for (int i = 0; i < steps.length; i++) {
            model.addElement(steps[i]);
        }
        stepList.setSelectedIndex(-1);
        stepList.ensureIndexIsVisible(0);
        stepList.repaint();
        levelComboBox.setSelectedIndex(-1);
        scoreTextField.setText("");
        
        // Baum neu laden
        buildTree();
    }
    
    public void buildTree() {
        CheckNode root = new CheckNode();
        for (int i = 0; i < steps.length; i++) {
            Enumeration en = root.children();
            CheckNode act = null;
            while (en.hasMoreElements()) {
                act = (CheckNode)en.nextElement();
                if (act.getCategory() == steps[i].getCategory()) {
                    break;
                }
                act = null;
            }
            if (act == null) {
                // neue Kategorie
                act = new CheckNode(steps[i].getCategoryName(), true,
                        steps[i].isEnabled() ? CheckNode.FULL : CheckNode.NONE,
                        null, false, false, false, steps[i].getCategory());
                root.add(act);
            }
            act.add(new CheckNode(steps[i].getType().getStepName(), false,
                    steps[i].isEnabled() ? CheckNode.FULL : CheckNode.NONE,
                    steps[i], false, false, false, null));
            if (act.getSelectionState() == CheckNode.FULL && ! steps[i].isEnabled()) {
                act.setSelectionState(CheckNode.HALF);
            }
            if (act.getSelectionState() == CheckNode.NONE && steps[i].isEnabled()) {
                act.setSelectionState(CheckNode.HALF);
            }
        }
        DefaultTreeModel tmpModel = new DefaultTreeModel(root);
        stepTree.setModel(tmpModel);
        stepTree.setShowsRootHandles(true);
        stepTree.setRootVisible(false);
        stepTree.setRowHeight(-1);
    }
    
    private void checkButtons(boolean setList) {
        boolean changeView = false;
        if (listView != setList) {
            changeView = true;
        }
        listView = setList;
        if (listView) {
            listButton.setSelected(true);
            treeButton.setSelected(false);
            if (changeView) {
                stepScrollPane.setViewportView(stepList);
                if (stepList.getSelectedIndex() >= 0) {
                    stepListValueChanged(null);
                }
            }
            stepList.requestFocusInWindow();
        } else {
            listButton.setSelected(false);
            treeButton.setSelected(true);
            if (changeView) {
                buildTree();
                stepScrollPane.setViewportView(stepTree);
                levelComboBox.setEnabled(false);
                scoreTextField.setEnabled(false);
            }
            stepTree.requestFocusInWindow();
        }
    }
    
    class CheckBoxRenderer extends JCheckBox implements ListCellRenderer {
        private static final long serialVersionUID = 1L;
        private boolean isTargetCell;
        private int index;
        
        public CheckBoxRenderer() {
        }
        
        @Override
        public Component getListCellRendererComponent(JList listBox, Object obj, int index,
                boolean isSelected, boolean hasFocus) {
//            System.out.println(((StepConfig)obj).toString());
            if (isSelected) {
                Color bg = UIManager.getColor("List.selectionBackground");
                if (bg == null) {
                    bg = UIManager.getColor("List[Selected].textBackground");
                }
                Color fg = UIManager.getColor("List.selectionForeground");
                if (fg == null) {
                    fg = UIManager.getColor("List[Selected].textForeground");
                }
                setBackground(bg);
                setForeground(fg);
//                System.out.println("SBG: " + bg);
//                System.out.println("SFG: " + fg);
                //necessary for Nimbus!
                setOpaque(true);
            } else {
                setBackground(UIManager.getColor("List.background"));
                setForeground(UIManager.getColor("List.foreground"));
//                System.out.println("BG: " + UIManager.getColor("List.background"));
//                System.out.println("FG: " + UIManager.getColor("List.foreground"));
                //necessary for Nimbus!
                setOpaque(false);
            }
            setText(((StepConfig)obj).toString());
            setSelected(((StepConfig)obj).isEnabled());

            isTargetCell = false;
            this.index = index;
            if (index == dropIndex) {
                isTargetCell = true;
            }
            return this;
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
//            System.out.println("paintComponent: " + getText()+ "/"+ getForeground() + "/" + getBackground());
            Graphics2D g2 = (Graphics2D) g;
            if (isTargetCell) {
                Insets insets = getInsets();
                g2.setColor(dndColor);
                g2.setStroke(dndStroke);
                //g2.drawLine(insets.left, 1, insets.right, 1);
                g2.drawLine(insets.left - 2, 0, insets.left - 2, 3);
                g2.drawLine(insets.left - 1, 2, getSize().width, 2);
            }
        }
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JComboBox levelComboBox;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JToggleButton listButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JTextField scoreTextField;
    private javax.swing.JList stepList;
    private javax.swing.JScrollPane stepScrollPane;
    private javax.swing.JTree stepTree;
    private javax.swing.JToggleButton treeButton;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables
    
    class MyDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            update(e);
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
            // we won't ever get this with a PlainDocument
        }
        private void update(DocumentEvent e) {
            String txt = scoreTextField.getText().trim();
            if (txt == null || txt.isEmpty()) {
                return;
            }
            StepConfig conf = (StepConfig)stepList.getSelectedValue();
            try {
                int value = Integer.parseInt(txt);
                conf.setBaseScore(value);
            } catch (NumberFormatException ex) {
                MessageFormat formatter = new MessageFormat(java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel.invalid_value").getString("MainFrame.invalid_filename"));
                String msg = formatter.format(new Object[] { txt });
                JOptionPane.showMessageDialog(null, msg,
                        java.util.ResourceBundle.getBundle("intl/ConfigSolverPanel").getString("ConfigSolverPanel.invalid_input"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
