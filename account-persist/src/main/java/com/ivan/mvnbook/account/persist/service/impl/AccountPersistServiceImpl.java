package com.ivan.mvnbook.account.persist.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ivan.mvnbook.account.persist.bean.Account;
import com.ivan.mvnbook.account.persist.service.AccountPersistService;
import com.ivan.mvnbook.common.exception.AccountPersistException;

public class AccountPersistServiceImpl implements AccountPersistService {

	private String file;

	private SAXReader reader = new SAXReader();

	private final String ElEMENT_ROOT = "account-persist";

	private final String ELEMENT_ACCOUNTS = "accounts";

	private final String ELEMENT_ACCOUNT = "account";

	private final String ELEMENT_ACCOUNT_ID = "id";

	private final String ELEMENT_ACCOUNT_NAME = "name";

	private final String ELEMENT_ACOUNT_EMAIL = "email";

	private final String ELEMENT_ACCOUNT_ACTIVATED = "activated";

	private Document readDocument() throws AccountPersistException {

		File dataFile = new File(file);

		if (!dataFile.exists()) {

			dataFile.getParentFile().mkdirs();
			Document document = DocumentFactory.getInstance().createDocument();
			Element rootEle = document.addElement(ElEMENT_ROOT);
			rootEle.addElement(ELEMENT_ACCOUNTS);
			writeDocument(document);
		}

		try {
			return reader.read(new File(file));
		} catch (DocumentException e) {
			throw new AccountPersistException("unable to read persist data xml", e);
		}
	}

	private void writeDocument(Document doc) throws AccountPersistException {
		Writer out = null;

		try {
			out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());

			writer.write(doc);

		} catch (IOException e) {
			throw new AccountPersistException("Unable to write persist data xml", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				throw new AccountPersistException("Unable to close persist data xml writer", e);
			}
		}
	}

	public Account createAccount(Account account) throws AccountPersistException {
		Document document = readDocument();
		Element rootElement = document.getRootElement();
		Element accountsEle = rootElement.element(ELEMENT_ACCOUNTS);
		Element accountEle = accountsEle.addElement(ELEMENT_ACCOUNT);
		accountEle.addElement(ELEMENT_ACCOUNT_ID).setText(account.getId());
		accountEle.addElement(ELEMENT_ACCOUNT_NAME).setText(account.getEmail());
		
		File dataFile = new File(file);
		if(dataFile.exists()){
			dataFile.delete();
		}
		
		writeDocument(document);
		return account;
	}

	public Account readAccount(String id) throws AccountPersistException {
		Document doc = readDocument();
		Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
		for (Element element : (List<Element>) accountsEle.elements()) {
			if (element.elementText(ELEMENT_ACCOUNT_ID).equals(id)) {
				return buildAccount(element);
			}
		}
		return null;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public SAXReader getReader() {
		return reader;
	}

	public void setReader(SAXReader reader) {
		this.reader = reader;
	}

	public Account updateAccount(Account account) throws AccountPersistException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAccount(String id) throws AccountPersistException {
		// TODO Auto-generated method stub

	}

	private Account buildAccount(Element element) {

		Account account = new Account();

		account.setId(element.elementText(ELEMENT_ACCOUNT_ID));
		account.setName(element.elementText(ELEMENT_ACCOUNT_NAME));
		account.setEmail(element.elementText(ELEMENT_ACOUNT_EMAIL));
		account.setActivated(("true".equals(element.elementText(ELEMENT_ACCOUNT_ACTIVATED)) ? true : false));

		return account;
	}

}
