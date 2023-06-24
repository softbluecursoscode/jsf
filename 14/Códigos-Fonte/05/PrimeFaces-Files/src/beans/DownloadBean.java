package beans;

import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@RequestScoped
public class DownloadBean implements Serializable {

	private StreamedContent file;
	
	public DownloadBean() {
		InputStream in = getClass().getResourceAsStream("/download/doc.pdf");
		file = new DefaultStreamedContent(in, "application/pdf", "download.pdf");
	}

	public StreamedContent getFile() {
		return file;
	}
}
