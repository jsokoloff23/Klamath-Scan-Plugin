/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.micromanager.ScanPlugin;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Raghu
 */
public class TableRenderer  extends JTextArea implements TableCellRenderer {
    public TableRenderer() {
    }
    
    @Override
    public Component getTableCellRendererComponent( JTable table, Object value,
            boolean isSelected, boolean hasFocus,int row, int column) {
        
        setLineWrap(true);
        setWrapStyleWord(true);
        
        if (value != null) {
            if (value.getClass() == ArrayList.class) {
                setText(value.toString());
            }
        } else {
            setText("");
        }
        
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        
        return this; 
    }
}