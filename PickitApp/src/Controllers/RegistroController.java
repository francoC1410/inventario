package Controllers;

import Models.Registro;
import Models.RegistroDao;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RegistroController implements ActionListener, MouseListener, KeyListener {

    private Registro registro;
    private RegistroDao registroDao;
    private SystemView views;
    DefaultTableModel models = new DefaultTableModel();

    public RegistroController(Registro registro, RegistroDao registroDao, SystemView views) {
        this.registro = registro;
        this.registroDao = registroDao;
        this.views = views;
        this.views.btn_register.addActionListener(this);
        this.views.btn_enter.addActionListener(this);
        this.views.txt_searchProduct.addKeyListener(this);
        this.views.txt_productDetails.addKeyListener(this);
        this.views.productDetailsTable.addMouseListener(this);
        this.views.btn_registerProfuct.addMouseListener(this);
        this.views.btn_openProduct.addMouseListener(this);
        this.views.btn_closeProduct.addMouseListener(this);
        this.views.btn_cancel.addActionListener(this);
        this.views.btn_total.addMouseListener(this);
        this.views.btn_deleteTable.addActionListener(this);
        this.views.btn_clearCampos.addMouseListener(this);
        this.views.txt_nameProduct.addKeyListener(this);
        this.views.btn_cantidadTotal.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register) {
            if (views.txt_codeProduct.getText().equals("")
                    || views.txt_nameProduct.getText().equals("")
                    || views.txt_productVencimiento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                registro.setCode(String.valueOf(views.txt_codeProduct.getText()));
                registro.setName(views.txt_nameProduct.getText().trim());
                registro.setDate(views.txt_productVencimiento.getText().trim());
                contador();
                if (views.txt_nameProduct.getText().equals(views.txt_nameProduct.getText().toUpperCase())) {
                    if (registroDao.registerProductQuery(registro) && registroDao.registerProductDetailsQuery(registro) && registroDao.registerProductTotalQuery(registro)) {
                        cleanFields();
                        JOptionPane.showMessageDialog(null, "Producto registrado con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el producto");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre debe estar en mayúsculas.");
                }
            }
        } else if (e.getSource() == views.btn_cantidadTotal) {
            registro.setName1(views.mLibre.getText());
            registro.setQuantity(Integer.parseInt(views.txt_cantidadM.getText()));
            registro.setName2(views.pickit.getText());
            registro.setQuantity2(Integer.parseInt(views.txt_cantidadP.getText()));
            if (registroDao.registerQuantityQuery(registro)) {
                cleanTable();
                listQuantityTotal();
                cleanFields();
            }
        } else if (e.getSource() == views.btn_enter) {
            registro.setId(Integer.parseInt(views.txt_productID.getText()));
            registro.setCode(String.valueOf(views.txt_searchCode.getText()));
            registro.setName(views.txt_searchName.getText().trim());
            registro.setPackages(String.valueOf(views.cmb_packages.getSelectedItem()));
            if (registroDao.updateProductQuery(registro) && registroDao.updateProductTotalQuery(registro)) {
                cleanTable();
                cleanFields();
                listPoduct();
                JOptionPane.showMessageDialog(null, "producto modificado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el producto");
            }
        } else if (e.getSource() == views.btn_deleteTable) {
            if (registroDao.deleteRowsExceptNullEgreso2() && registroDao.deleteProductQuery()) {
                cleanTable();
                cleanFields();
                listPoduct();
                listAllPoduct();
                listPoductTotal();
                JOptionPane.showMessageDialog(null, "Producto eliminado con éxito");
            }
        } else if (e.getSource() == views.btn_cancel) {
            cleanFields();
        }
    }

    public void listAllPoduct() {
        List<Registro> list = registroDao.listProductQuery(views.txt_searchProduct.getText());
        models = (DefaultTableModel) views.productTable.getModel();
        Object[] row = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getCode();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getCreated();
            row[3] = list.get(i).getDate();
            models.addRow(row);
        }
        views.productTable.setModel(models);
    }

    public void listPoduct() {
        List<Registro> list = registroDao.listProductDetailsQuery(views.txt_productDetails.getText());
        models = (DefaultTableModel) views.productDetailsTable.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getCode();
            row[2] = list.get(i).getName();
            row[3] = list.get(i).getPackages();
            row[4] = list.get(i).getUpdated();
            models.addRow(row);
        }
        views.productDetailsTable.setModel(models);
    }

    public void listPoductTotal() {
        List<Registro> list = registroDao.listProductTotalQuery();
        models = (DefaultTableModel) views.totalTable.getModel();
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getCode();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getCreated();
            row[3] = list.get(i).getUpdated();
            row[4] = list.get(i).getPackages();
            row[5] = list.get(i).getDate();
            models.addRow(row);
        }
        views.totalTable.setModel(models);
    }

    public void listQuantityTotal() {
        List<Registro> list = registroDao.listQuantityTotalQuery();
        models = (DefaultTableModel) views.cantidadTabla.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getName1();
            row[1] = list.get(i).getQuantity();
            row[2] = list.get(i).getName2();
            row[3] = list.get(i).getQuantity2();
            row[4] = list.get(i).getCreated();
            models.addRow(row);
        }
        views.cantidadTabla.setModel(models);
    }

    public void cleanTable() {
        for (int i = 0; i < models.getRowCount(); i++) {
            models.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        views.txt_codeProduct.setText("");
        views.txt_nameProduct.setText("");
        views.txt_productVencimiento.setText("");
        views.txt_productID.setText("");
        views.txt_searchCode.setText("");
        views.txt_searchName.setText("");
        views.txt_productDetails.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.productDetailsTable) {
            int row = views.productDetailsTable.rowAtPoint(e.getPoint());
            views.txt_productID.setText(views.productDetailsTable.getValueAt(row, 0).toString());
            views.txt_searchCode.setText(views.productDetailsTable.getValueAt(row, 1).toString());
            views.txt_searchName.setText(views.productDetailsTable.getValueAt(row, 2).toString());
        } else if (e.getSource() == views.btn_openProduct) {
            views.jTabbedPane1.setSelectedIndex(1);
            cleanTable();
            listAllPoduct();
        } else if (e.getSource() == views.btn_closeProduct) {
            views.jTabbedPane1.setSelectedIndex(2);
            cleanTable();
            listPoduct();
        } else if (e.getSource() == views.btn_registerProfuct) {
            views.jTabbedPane1.setSelectedIndex(0);
            cleanTable();
            listQuantityTotal();
            calculateMercadoLibre();
            calculatePickit();
        } else if (e.getSource() == views.btn_total) {
            views.jTabbedPane1.setSelectedIndex(3);
            cleanTable();
            listPoductTotal();
        } else if (e.getSource() == views.btn_clearCampos) {
            cleanFields();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_nameProduct && e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Obtener la fecha actual
            Calendar calendar = Calendar.getInstance();

            // Agregar 7 días
            calendar.add(Calendar.DAY_OF_MONTH, 7);

            // Obtener la nueva fecha
            Date nuevaFecha = calendar.getTime();

            // Ahora, puedes usar nuevaFecha como necesites.
            // Por ejemplo, imprimir la fecha en la consola:
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = dateFormat.format(nuevaFecha);
            String fecha = fechaFormateada;
            views.txt_productVencimiento.setText(fecha);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_searchProduct) {
            cleanTable();
            listAllPoduct();
        } else if (e.getSource() == views.txt_productDetails) {
            cleanTable();
            listPoduct();
        }

    }

    private static int cantidad = 0;
    private static int cantidad2 = 0;

    public void contador() {
        String inputText = views.txt_codeProduct.getText().trim();

        if (inputText.matches("\\d{9,25}")) {
            if (cantidad < 50) {
                cantidad++;
            }
        }

        if (inputText.matches(".\\d{9,25}") && inputText.matches(".*[a-zA-Z].*")) {
            if (cantidad2 < 50) {
                cantidad2++;
            }
        }

        views.txt_cantidadM.setText(String.valueOf(cantidad));
        views.txt_cantidadP.setText(String.valueOf(cantidad2));
    }

    public void calculateMercadoLibre() {
        int total = 0;
        int numRow = views.cantidadTabla.getRowCount();
        for (int i = 0; i < numRow; i++) {
            Object value = views.cantidadTabla.getValueAt(i, 1);
            if (value instanceof Number number) {
                total += number.doubleValue();
            }
        }
        views.txt_cantidadMercadoLibre.setText(String.valueOf(total));
    }

    public void calculatePickit() {
        int total = 0;
        int numRow = views.cantidadTabla.getRowCount();
        for (int i = 0; i < numRow; i++) {
            Object value = views.cantidadTabla.getValueAt(i, 3);
            if (value instanceof Number number) {
                total += number.doubleValue();
            }
        }
        views.txt_cantidadPickit.setText(String.valueOf(total));
    }

}
