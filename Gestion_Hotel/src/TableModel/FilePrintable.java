package TableModel;

import java.awt.print.PrinterException;

public class FilePrintable implements java.awt.print.Printable {
    private String fileName;

    public FilePrintable(String fileName) {
        this.fileName = fileName;
    }

    public int print(java.awt.Graphics g, java.awt.print.PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fileName));
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                g.drawString(line, 0, y);
                y += 10; // Espacement entre les lignes
            }
            br.close();
        } catch (java.io.IOException e) {
            System.err.println("Erreur lors de l'impression du fichier: " + e.getMessage());
        }

        return PAGE_EXISTS;
    }
}
