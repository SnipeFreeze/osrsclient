/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.hiscores;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.RuneScapeAccount;
import net.miginfocom.swing.MigLayout;
import rsclient.coregui.Style;
import logic.Calculate;

/**
 *
 * @author ben
 */
public class LevelScorePanel extends JPanel implements MouseListener {

    private String skill;
    private JLabel skillImageLabel;
    private JLabel skillLevelLabel;
    private StatRolloverListener rolloverListener;
    private Style style;

    public LevelScorePanel(String skill, Style style) {
        this.skill = skill;
        this.style = style;
        setup();
    }

    private void setup() {
        this.setLayout(new MigLayout("ins 0"));
        skillLevelLabel = new JLabel("-");
        skillLevelLabel.setFont(style.font);
        skillLevelLabel.setForeground(style.foregroundColor);
        this.setBackground(style.backgroundColor);
        skillLevelLabel.addMouseListener(this);

        skillImageLabel = new JLabel(
                new ImageIcon(getClass().getClassLoader().getResource("resources/logo_" + skill + ".gif")));
        skillImageLabel.addMouseListener(this);

        add(skillImageLabel, "cell 0 0, ");
        add(skillLevelLabel, "cell 1 0,");
    }
    
    public void updateLevelStat(RuneScapeAccount account){
        int level = account.hiscores.get(skill).level;
        if(level == 99){
            int exp = account.hiscores.get(skill).experience;
            if(exp == 200000000) {
                skillLevelLabel.setText("MAX");
                return;
            }
            while(exp > Calculate.xpForLevel(level + 1)){
                level++;
            }
        } 
        skillLevelLabel.setText("" + level);
        
    }
    
    public void nullLevelStat(){
        skillLevelLabel.setText("-");
    }
    
    public void setLevelText(String level){
        skillLevelLabel.setText(level);
    }
    
    public String getSkill(){
        return skill;
    }
    
    public void setRolloverListener(StatRolloverListener rolloverListener){
        this.rolloverListener = rolloverListener;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (rolloverListener != null) {
            rolloverListener.onRolledOver(this);
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (rolloverListener != null) {
            rolloverListener.onRolledOff(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

}
