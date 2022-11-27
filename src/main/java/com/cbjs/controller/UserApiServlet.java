package com.cbjs.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cbjs.dao.UserDao;
import com.cbjs.model.User;
import com.mysql.cj.protocol.a.NativeConstants.IntegerDataType;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@WebServlet(urlPatterns = { "/api/user" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UserApiServlet extends HttpServlet {
	private UserDao userDao = new UserDao();
	private byte[] data = new byte[4];
	private static final int BUFFER_SIZE = 4096;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		String id = req.getParameter("id");
		String action = req.getParameter("action");

		if (action != null) {
			switch (action) {
				case "private_info": {
					if (id != null) {
						try {
							User user = userDao.getInfo(id);
							JSONObject userJson = new JSONObject();
							userJson.put("id", user.getId());
							userJson.put("username", user.getUsername());
							userJson.put("email", user.getEmail());
							userJson.put("money", user.getMoney());
							userJson.put("bio", user.getBio());
							userJson.put("credit_card", user.getCreditCard());
							userJson.put("avatar", user.getAvatar());
							userJson.put("kyc", user.isKyc());
							out.println(userJson);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						out.print("Missing param !!!");
					}
					break;
				}
				case "public_info": {
					if (id != null) {
						try {
							User user = userDao.getInfo(id);
							JSONObject userJson = new JSONObject();
							userJson.put("id", user.getId());
							userJson.put("username", user.getUsername());
							userJson.put("money", user.getMoney());
							userJson.put("bio", user.getBio());
							userJson.put("avatar", user.getAvatar());
							out.println(userJson);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						out.print("Missing param !!!");
					}
					break;
				}
				case "ranking": {
					JSONArray listUserJson = new JSONArray();
					try {
						ArrayList<User> listUser = userDao.getTopUser();
						for (User user : listUser) {
							JSONObject userJson = new JSONObject();
							userJson.put("id", user.getId());
							userJson.put("username", user.getUsername());
							userJson.put("money", user.getMoney());
							listUserJson.add(userJson);
						}
						out.println(listUserJson);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					break;
				}
				default:
					out.print("Wrong action !!!");
					break;
			}
		} else {
			out.print("Missing param !!!");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		String id = req.getParameter("id");
		String credit_card = req.getParameter("credit_card");
		String email = req.getParameter("email");
		String bio = req.getParameter("bio");
		PrintWriter out = resp.getWriter();
		String action = req.getParameter("action");
		String paramMoney = req.getParameter("money");

		if (action != null) {
			switch (action) {
				case "update_avatar": {
					if (id != null) {
						String url = "";
						for (Part part : req.getParts()) {
							String fileName = extractFileName(part);
							fileSignature(part.getInputStream());
							String mimeType = getFileType(data);
							String[] whiteList = { "jpg", "png", "gif" };
							if (!Arrays.asList(whiteList).contains(mimeType)) {
								out.print("Your file is not accepted!!");
								return;
							}
							// refines the fileName in case it is an absolute path
							fileName = new File(fileName).getName();
							String randomFileName = usingRandomUUID();
							String extentionFile = getFileExtension(fileName);
							String newFileName = randomFileName + extentionFile;
							url = "/JavaCBJS-1/upload/" + newFileName;
							try {
								userDao.updateAvatar(id, newFileName);
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							resp.sendRedirect("/JavaCBJS-1/profile");
							part.write(this.getFolderUpload().getAbsolutePath() + File.separator + newFileName);
						}
					} else {
						out.print("Missing param !!!");
					}

					break;
				}
				case "update_kyc": {
					if (id != null) {
						for (Part part : req.getParts()) {
							String fileName = extractFileName(part);
							// refines the fileName in case it is an absolute path
							fileName = new File(fileName).getName();
							if (fileName.endsWith(".zip")) {
								ZipInputStream zipIn = new ZipInputStream(part.getInputStream());
								ZipEntry entry = zipIn.getNextEntry();
								// iterates over entries in the zip file
								String randomFolderName = usingRandomUUID();
								while (entry != null) {
									String filePath = this.getFolderUploadZip(randomFolderName).getAbsolutePath() + File.separator + entry.getName();
									if (!entry.isDirectory()) {
										// if the entry is a file, extracts it
										extractFile(zipIn, filePath);
									} else {
										// if the entry is a directory, make the directory
										File dir = new File(filePath);
										dir.mkdir();
									}
									zipIn.closeEntry();
									entry = zipIn.getNextEntry();
								}
								zipIn.close();
								resp.sendRedirect("/JavaCBJS-1/profile");
							} else {
								out.print("Wrong File!!! Just Upload a zip file!!!");
								return;
							}
						}
					} else {
						out.print("Missing param !!!");
					}
					break;
				}
				case "update_profile": {
					if (id != null && bio != null && credit_card != null && email != null) {
						try {
							userDao.updateProfile(id, bio, credit_card, email);
							resp.sendRedirect("/JavaCBJS-1/profile");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						out.print("Missing param");
					}
					break;
				}
				case "update_money": {
					if (id != null && paramMoney != null) {
						int money = Integer.parseInt(paramMoney);
						try {
							userDao.updateMoney(id, money);
							out.print("Update money success");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						out.print("Missing param !!!");
					}
					break;
				}
				default:
					out.print("Wrong action !!!");
					break;
			}
		} else {
			out.print("Missing param !!!");
		}
	}

	// Random String
	static String usingRandomUUID() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replaceAll("-", "");

	}

	// Get the file type based on the file signature.
	private void fileSignature(InputStream is) throws IOException, NullPointerException {
		is.read(data, 0, 4);
	}

	// get file Type
	private String getFileType(byte[] fileData) {
		String type = "undefined";
		if (Byte.toUnsignedInt(fileData[0]) == 0x89 && Byte.toUnsignedInt(fileData[1]) == 0x50)
			type = "png";
		else if (Byte.toUnsignedInt(fileData[0]) == 0xFF && Byte.toUnsignedInt(fileData[1]) == 0xD8)
			type = "jpg";
		else if (Byte.toUnsignedInt(fileData[0]) == 0x47 && Byte.toUnsignedInt(fileData[1]) == 0x49)
			type = "gif";

		return type;
	}

	// Get File Extension
	static String getFileExtension(String name) {
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	// extractFile to folder
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
			byte[] bytesIn = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = zipIn.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
		}
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	// return url folder upload
	public File getFolderUpload() {
		File folderUpload = new File(System.getProperty("catalina.home") + "/webapps/JavaCBJS-1/upload");
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}

	public File getFolderUploadZip(String folderName) {
		File folderUpload = new File(
				System.getProperty("catalina.home") + "/webapps/JavaCBJS-1/upload/kyc/" + folderName);
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}
}
