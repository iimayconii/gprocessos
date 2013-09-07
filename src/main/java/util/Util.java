/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.GenericEntity;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.imageio.ImageIO;
import javax.persistence.Table;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.hibernate.internal.util.JdbcExceptionHelper;

/**
 * Classe diversas funções uteis para o sistema
 *
 * @author maycon
 * @version 1.0
 *
 */
public class Util {

    public static String previousPagUrl = "";

    /**
     * Metodo que tem como função retornar o nome da tabela de uma classe que
     * está anotada como uma entidade.
     *
     * @param clazz
     * @return o nome da tabela
     * @since 1.0
     */
    public static String getTableName(Class clazz) {
        //System.out.println(clazz.getSimpleName());
        Table annotation = (Table) clazz.getAnnotation(javax.persistence.Table.class);
        if (annotation != null) {
            //System.out.println(annotation.name());
            return annotation.name();
        }
        return null;
    }

    public static <T> List<T> mergeTwoArrays(List<T> array, List<T> otherArray) {
        boolean match = false;
        List<T> temp = array.subList(0, array.size());
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < otherArray.size(); j++) {
                if (((GenericEntity) array.get(i)).getId().intValue() == ((GenericEntity) otherArray.get(j)).getId()) {
                    otherArray.remove(j);
                    match = true;
                    break;
                }
            }
            if (match) {
                temp.remove(array.get(i));
                match = false;
            }
        }
        return temp;
    }

    /**
     * Metodo que retorna a data atual
     *
     * @return a data atual
     * @since 1.0
     */
    public static Date getDate() {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-3"), new Locale("pt_BR"));
        return calendar.getTime();

    }

    /**
     * Formata a data
     *
     * @param date
     * @return a data formatada
     */
    public static String dateFormat(Date date) {
        return null;

    }

    /**
     * Metodo responsavel por retornar o caminho da pasta de arquivos do sistema
     *
     * @return o caminho da pasta dos arquivos
     * @since 1.0
     */
    public static String getMediaPath() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        return (servletContext.getRealPath("") + File.separator + "archives" + File.separator);
    }

    /**
     * Esse metodo retora um numero randomico
     *
     * @return um numero randomico
     * @since 1.0
     */
    public static String getRandomName() {
        int i = (int) (Math.random() * 10000000);
        return String.valueOf(i);
    }

    /**
     * Metodo responsavel por criptografar uma string em md5
     *
     * @param aToken
     * @return string criptogradada
     * @since 1.0
     */
    public static String generateMD5(String aToken) {
        MessageDigest aMessageDigest = null;
        try {
            aMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algoritmo solicitado não encontrado.");
        }
        BigInteger hash = new BigInteger(1, aMessageDigest.digest(aToken.getBytes()));
        String aMD5 = hash.toString(16);
        return aMD5;
    }

    /**
     * Este metodo e responsavel por retirar os acentos de um string
     *
     * @param acentuada
     * @return string sem acentos
     * @since 1.0
     */
    public static String removerAcentos(String acentuada) {
        System.out.println("acentuada: " + acentuada);
        CharSequence cs = new StringBuilder(acentuada);
        String normalizada = Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        normalizada = normalizada.replace(" ", "_");
        normalizada = normalizada.replace(".", "");
        normalizada = normalizada.replace("/", "");
        normalizada = normalizada.replace("+", "");
        normalizada = normalizada.replace(":", "");
        normalizada = normalizada.replace("=", "");
        normalizada = normalizada.replace("ç", "c");
        normalizada = normalizada.replace("[", "_");
        normalizada = normalizada.replace("]", "_");
        normalizada = normalizada.toLowerCase();
        return normalizada;
    }

    /**
     * Este metodo retorna a extensão de um arquivo
     *
     * @param acentuada
     * @return extensão de um arquivo
     * @since 1.0
     */
    public static String getextension(String aFilename) {
        return aFilename.substring(aFilename.lastIndexOf('.') + 1);
    }

    public static List<String> readerCSV(FileInputStream csv) {
        return readerCSV(csv);
    }

    public static List<String> readerCSV(InputStream stream) {
        BufferedReader bin = null;
        List<String> data = new ArrayList<String>();
        try {
            bin = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = null;
            while ((line = bin.readLine()) != null) {
                String[] split = line.split(";");
                for (String value : split) {
                    if (!value.isEmpty()) {
                        data.add(value);
                    } else {
                        data.add("");
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public static byte[] createThumbnail(byte[] orig, int maxDim, int t) {
        try {
            ImageIcon imageIcon = new ImageIcon(orig);
            java.awt.Image inImage = imageIcon.getImage();
            double scale = (double) maxDim / (double) inImage.getWidth(null);
            double scale2 = (double) t / (double) inImage.getHeight(null);

            int scaledW = (int) (scale * inImage.getWidth(null));
            int scaledH = (int) (scale2 * inImage.getHeight(null));

            BufferedImage outImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
            AffineTransform tx = new AffineTransform();

            if (scale < 1.0d) {
                tx.scale(scale, scale2);
            }

            Graphics2D g2d = outImage.createGraphics();
            g2d.drawImage(inImage, tx, null);
            g2d.dispose();


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(outImage, "JPG", baos);
            byte[] bytesOut = baos.toByteArray();

            return bytesOut;
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static double porcent(double total, double value) {
        return ((value * 100) / total);
    }
   
    public static byte[] fileToByte(File file) throws Exception {
        return Util.fileToByte(new FileInputStream(file));
    }

    public static byte[] fileToByte(InputStream fis) throws Exception {        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
        }
        return bos.toByteArray();
    }
}
