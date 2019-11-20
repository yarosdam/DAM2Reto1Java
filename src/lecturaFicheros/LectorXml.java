package lecturaFicheros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import logs.Logger;
import modelo.objetos.Centro;
import modelo.objetos.Departamento;

public class LectorXml {

	private List<Departamento> departamentos;
	private Centro centro;
	private Logger log = Logger.getInstance();

	public LectorXml() {
		departamentos = new ArrayList<Departamento>();
		centro = new Centro();
	}

	public List<Departamento> leerXml(String path) {
		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = parserFactory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {
				boolean bDepartamento = false;
				boolean bCentro = false;
				int id = 0;

				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("centro")) {
						centro = new Centro(Integer.parseInt(attributes.getValue("id")), attributes.getValue("nombre"));
						bCentro = true;
					}
					if (qName.equalsIgnoreCase("departamento")) {
						id = Integer.parseInt(attributes.getValue("id"));
						bDepartamento = true;
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					if (bDepartamento) {
						if (bCentro) {
							Departamento depar = new Departamento(id, new String(ch, start, length), centro);
							departamentos.add(depar);
							bDepartamento = false;
						} else {
							Departamento depar = new Departamento(id, new String(ch, start, length), null);
							departamentos.add(depar);
							bDepartamento = false;
						}
					}
				}
			};
			File file = new File(path);
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);
		} catch (FileNotFoundException e) {
			log.loggear("Error, archivo no encontrado en la lectura de xml",this.getClass(), 2);
		} catch (Exception e) {
			log.loggear("Error en la lectura del archivo xml",this.getClass(), 2);
		}
		return departamentos;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centros) {
		this.centro = centros;
	}
}
