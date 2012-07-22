package game.util;

import game.packages.AbstractPacket;
import game.packages.IPacket;
import game.packages.PacketDescrip;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * ��̬�������еı���
 * @author admin
 *
 */


public class PackageUtil {

	/**
	 * �Ӱ�package�л�ȡ���е�Class
	 * 
	 * @param pack
	 * @return
	 */
	public static List<Class<?>> getClasses ( String pack ) {

		// ��һ��class��ļ���
		List<Class<?>> classes = new ArrayList<Class<?>>();

		boolean recursive = true;// �Ƿ�ѭ������

		String packageName = pack;// ��ȡ�������� �������滻
		String packageDirName = packageName.replace( '.', '/' );// ����һ��ö�ٵļ���
																// ������ѭ�����������Ŀ¼�µ�things

		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources( packageDirName );
			while (dirs.hasMoreElements()) {

				URL url = dirs.nextElement();
				String protocol = url.getProtocol();// �õ�Э�������

				if ("file".equals( protocol )) {// ��������ļ�����ʽ�����ڷ�������
					System.err.println( "file���͵�ɨ��" );
					String filePath = URLDecoder.decode( url.getFile(), "UTF-8" );// ��ȡ��������·��
					findAndAddClassesInPackageByFile( packageName, filePath, recursive, classes );// ���ļ��ķ�ʽɨ���������µ��ļ�
																									// ����ӵ�������
				} else if ("jar".equals( protocol )) {

					System.err.println( "jar���͵�ɨ��" );
					JarFile jar;
					try {

						jar = ((JarURLConnection) url.openConnection()).getJarFile();// �Ӵ�jar�� �õ�һ��ö����

						Enumeration<JarEntry> entries = jar.entries();// ͬ���Ľ���ѭ������

						while (entries.hasMoreElements()) {

							JarEntry entry = entries.nextElement();
							String name = entry.getName();

							if (name.charAt( 0 ) == '/') {// �������/��ͷ��
								name = name.substring( 1 );
							}
							// ���ǰ�벿�ֺͶ���İ�����ͬ
							if (name.startsWith( packageDirName )) {
								int idx = name.lastIndexOf( '/' );
								// �����"/"��β ��һ����
								if (idx != -1) {
									// ��ȡ���� ��"/"�滻��"."
									packageName = name.substring( 0, idx ).replace( '/', '.' );
								}
								// ������Ե�����ȥ ������һ����
								if ((idx != -1) || recursive) {
									// �����һ��.class�ļ� ���Ҳ���Ŀ¼
									if (name.endsWith( ".class" ) && !entry.isDirectory()) {
										// ȥ�������".class" ��ȡ����������
										String className = name.substring( packageName.length() + 1, name.length() - 6 );
										try {
											// ��ӵ�classes
											classes.add( Class.forName( packageName + '.' + className ) );
										} catch (ClassNotFoundException e) {
											// log
											// .error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("��ɨ���û�������ͼʱ��jar����ȡ�ļ�����");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * ���ļ�����ʽ����ȡ���µ�����Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile ( String packageName, String packagePath, final boolean recursive, List<Class<?>> classes ) {
		// ��ȡ�˰���Ŀ¼ ����һ��File
		File dir = new File( packagePath );

		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("�û�������� " + packageName + " ��û���κ��ļ�");
			return;
		}

		File[] dirfiles = dir.listFiles();
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile( packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes );
			} else {
				String className = file.getName().substring( 0, file.getName().length() - 6 );// �����java���ļ�
																								// ȥ�������.class
																								// ֻ��������
				try {
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// �����ظ�ͬѧ�����ѣ�������forName��һЩ���ã��ᴥ��static������û��ʹ��classLoader��load�ɾ�
					classes.add( Thread.currentThread().getContextClassLoader().loadClass( packageName + '.' + className ) );
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * ��ӡ���еİ����
	 */
	static void printAllPakcets( AbstractPacket[] packets ){
		Formatter f = new Formatter( System.out );
		f.format( "%-15s %-127s %-150s \n", "����", "���", "����˵��" );
		f.format( "%-15s %-127s %-150s \n", "����", "����", "��������" );
		for (AbstractPacket ap : packets) {

			if (ap != null) {
				Class<?> c = ap.getClass();
				PacketDescrip desc = c.getAnnotation( PacketDescrip.class );
				f.format( "%-8s %-50s %-150s \n", ap.getPacketNo(), c.getName(), desc.desc() );
				
			}
		}
	}

	public static void main ( String[] args ) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException {
		AbstractPacket p1 = null;
		//String p = "PacketTest";
		String p = "game.packages";//���ļ���
		List<Class<?>> list = getClasses( p );
		int max = 1000;
		final AbstractPacket[] packets = new AbstractPacket[max];//������0�Ű�

		// �������а���ʵ�����飬���������
		for (Class<?> c : list) {
			if (!c.isInterface() && !c.getName().contains( "Abstract" )) {

				p1 = (AbstractPacket) c.newInstance();
				//System.out.println( c.getName() + " ��" + p1.getPacketNo() );

				int packetNo = p1.getPacketNo();
				AbstractPacket ip = packets[packetNo];
				if (ip == null) {
					packets[packetNo] = p1;
					
				} else {
					System.out.println( packetNo + " �ظ���" );
				}
			}
		}

		System.out.println( "---------------------------------------------------" );
		for (int i = 0; i < 100; i++) {
			int packetNo = new Random().nextInt( packets.length );
			IPacket ap = packets[packetNo];
			System.out.print( i + ":\t" );
			if (ap == null) {
				System.out.println( packetNo + "�������ڶ�Ӧ�İ���" );
			} else {
				ap.run( null, null );
				
				if( ap.getPacketNo() == 4 ){
					//((ShowBattle)ap).run( null, true, 234 );
				}
			}		

		}
		System.out.println( "---------------------------------------------------" );

		printAllPakcets( packets );
		
		
		
	}
}
