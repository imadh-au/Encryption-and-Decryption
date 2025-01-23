package dm;
import java.io.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.jface.resource.FontDescriptor;

public class Window {
	private static final String FILE_PATH = "C:\\Users\\HP\\Desktop\\caesar_encrypted.txt";
	private static final String FILE_PATHRSA = "C:\\Users\\HP\\Desktop\\rsa_encrypted.txt";
	protected Shell shell;
	CeaserCipher caesar = new CeaserCipher();
	RSA rsa = new RSA();
	BreakCeaserCipher breaker = new BreakCeaserCipher();
	private Text txtinput;
	private Text txtoutput;
	private Text txtmodulu;
	private Text txtshift;
	private LocalResourceManager localResourceManager;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Window window = new Window();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showMessage(String title, String message) {
	    MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
	    messageBox.setText(title);
	    messageBox.setMessage(message);
	    messageBox.open();
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		createResourceManager();
		shell.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		shell.setSize(717, 524);
		shell.setText("SWT Application");
		
		Label lblInputText = new Label(shell, SWT.NONE);
		lblInputText.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		lblInputText.setBounds(87, 54, 102, 21);
		lblInputText.setText("Input Text");
		
		txtinput = new Text(shell, SWT.BORDER);
		txtinput.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 9, SWT.NORMAL)));
		txtinput.setBounds(284, 54, 225, 24);
		
		Label lblEncrytedDecryptedText = new Label(shell, SWT.NONE);
		lblEncrytedDecryptedText.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		lblEncrytedDecryptedText.setBounds(87, 173, 172, 21);
		lblEncrytedDecryptedText.setText("Encryted/ Decrypted Text");
		
		Label lblrsakey = new Label(shell, SWT.NONE);
		lblrsakey.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 8, SWT.NORMAL)));
		lblrsakey.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		lblrsakey.setBounds(284, 264, 225, 75);
		lblrsakey.setText("RSA Keys");
		
		txtoutput = new Text(shell, SWT.BORDER);
		txtoutput.setBounds(284, 174, 225, 24);
		List list = new List(shell, SWT.BORDER);
		list.setTouchEnabled(true);
		list.setItems(new String[] {"General Caeser Cipher", "RSA", "Break Caeser Cipher without Key"});
		list.setBounds(284, 234, 225, 24);
		
		Button btnEncrypt = new Button(shell, SWT.FLAT);
		btnEncrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
				if(list.getSelectionIndex()==0) {
					String caesarPlainText = txtinput.getText();
					int a = Integer.parseInt(txtmodulu.getText());
					int b = Integer.parseInt(txtshift.getText());
					String caesarEncrypted = caesar.encrypt(caesarPlainText, a, b);
					txtoutput.setText(caesarEncrypted);
					saveToFile(FILE_PATH, caesarEncrypted);
					
				}
				else if(list.getSelectionIndex()==1) {
					rsa.generateKeysWithRandomPrimes();
					lblrsakey.setText(rsa.getrakeys());
					String rsaEncrypted = rsa.encryptString(txtinput.getText());
					txtoutput.setText(rsaEncrypted);
					saveToFile(FILE_PATHRSA, rsaEncrypted);

				}
				}catch(Exception ex) {
					 showMessage("Error", ex.getMessage());
				}
				
			}
		});
		btnEncrypt.setBounds(175, 394, 122, 47);
		btnEncrypt.setText("Encrypt");
		
		Button btnDecrypt = new Button(shell, SWT.FLAT);
		btnDecrypt.setForeground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(64, 0, 128))));
		btnDecrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
		            if (list.getSelectionIndex() == 0) {
		                String caesarCipherText = readFromFile(FILE_PATH);
		                int a = Integer.parseInt(txtmodulu.getText());
		                int b = Integer.parseInt(txtshift.getText());
		                String caesarDecrypted = caesar.decrypt(caesarCipherText, a, b);
		                txtoutput.setText(caesarDecrypted);
		            } else if (list.getSelectionIndex() == 1) {
		                String rsaCipherText = readFromFile(FILE_PATHRSA);
		                String rsaDecrypted = rsa.decryptString(rsaCipherText);
		                txtoutput.setText(rsaDecrypted);
		            } else if (list.getSelectionIndex() == 2) {
		                String cipherText = txtinput.getText();
		                String brokenText = breaker.breakCipher(cipherText);
		                txtoutput.setText(brokenText);
		            }
		        } catch (Exception ex) {
		            showMessage("Error", ex.getMessage());
		        }
			}
		});
		btnDecrypt.setBounds(349, 394, 122, 47);
		btnDecrypt.setText("Decrypt");
		
		
		
		txtmodulu = new Text(shell, SWT.BORDER);
		txtmodulu.setBounds(284, 94, 43, 24);
		
		txtshift = new Text(shell, SWT.BORDER);
		txtshift.setBounds(284, 124, 43, 24);
		
		Label lblModulo = new Label(shell, SWT.NONE);
		lblModulo.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		lblModulo.setText("Invertible Modulo 256");
		lblModulo.setBounds(87, 94, 156, 21);
		
		Label lblShiftIndex = new Label(shell, SWT.NONE);
		lblShiftIndex.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		lblShiftIndex.setText("Shift Index");
		lblShiftIndex.setBounds(87, 127, 102, 21);
		
		Label lblSelectgccrsa = new Label(shell, SWT.NONE);
		lblSelectgccrsa.setText("Select(GCC/RSA):");
		lblSelectgccrsa.setBackground(localResourceManager.create(ColorDescriptor.createFrom(new RGB(255, 255, 255))));
		lblSelectgccrsa.setBounds(87, 234, 172, 21);
		
		

	}
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),shell);
	}
	
	// Method to save text to a file
	private static void saveToFile(String fileName, String content) {
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	writer.write(content);
	} catch (IOException e) {
	System.out.println("Error writing to file: " + e.getMessage());
	}
	}

	// Method to read text from a file
	private static String readFromFile(String fileName) {
	try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	return reader.readLine();
	} catch (IOException e) {
	System.out.println("Error reading from file: " + e.getMessage());
	return null;
	}
	}
}
