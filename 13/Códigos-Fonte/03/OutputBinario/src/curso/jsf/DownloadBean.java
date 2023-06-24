package curso.jsf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

@Named("bean")
@RequestScoped
public class DownloadBean implements Serializable {

	public String download() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) ec.getResponse();
		
		response.setContentType("application/pdf");
		
		// Indique um arquivo que exista no seu computador
		File file = new File("d:/temp/softblue.pdf");
		
		response.setContentLength((int) file.length());
		
		// Descomente a linha abaixo se você quiser forçar o browser a fazer o download,
		// ao invés de tentar exibir o documento dentro do próprio browser
		// response.setHeader("Content-Disposition", "attachment;filename=\"" + "softblue.pdf" + "\"");
		
		try {
			InputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fc.responseComplete();
		return null;
	}

	private void copy(InputStream in, OutputStream out) {
		byte[] buffer = new byte[1024];
		int len;

		try {
			try {
				while (true) {
					len = in.read(buffer);
					if (len < 0) {
						break;
					}
					out.write(buffer, 0, len);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
