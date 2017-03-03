import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

import baoutil.LogB;
import LinearKalmanFilter.LKFTestCannon;

import java.util.ArrayList;

public class Test extends JFrame {

	private Font font = new Font("serif", Font.ITALIC + Font.BOLD, 12);
	protected Action newAction, openAction;
	static final JMenuBar mainMenuBar = new JMenuBar();
	protected JMenu fileMenu, editMenu;

	private int x0 = 50, y0 = 550, w = 700, h = 500;
	private int frameWidth = w + 100, frameHeight = h + 100;
	private ArrayList testResult;
	private int resultCount;
	private int[] x;
	private int[] y;
	private int[] nx;
	private int[] ny;
	private int[] kx;
	private int[] ky;
	private long timeUsed;

	public Test() {

		super("");
		setTitle("�����������˲�");
		createActions();
		addMenus();
		this.getContentPane().setBackground(Color.BLACK);
		this.setDefaultCloseOperation(3);//�˳�ʱ�رչ�����Դ
		
		setSize(frameWidth, frameHeight);
		setVisible(true);

		timeUsed = System.currentTimeMillis();
		testResult = LKFTestCannon.test();
		timeUsed = System.currentTimeMillis() - timeUsed;//�����ʱ
		//����
		getCoordinates();
	}
    /**
     * �������
     */
	private void getCoordinates() {
		resultCount = testResult.size();
		x = new int[resultCount];
		y = new int[resultCount];
		nx = new int[resultCount];
		ny = new int[resultCount];
		kx = new int[resultCount];
		ky = new int[resultCount];

		for (int i = 0; i < resultCount; i++) {
			LKFTestCannon.TestResult tr = (LKFTestCannon.TestResult) testResult
					.get(i);
			x[i] = (int) (tr.x / 1200.0 * w + x0);
			y[i] = (int) (y0 - tr.y / 500.0 * h);
			nx[i] = (int) (tr.nx / 1200.0 * w + x0);
			ny[i] = (int) (y0 - tr.ny / 500.0 * h);
			kx[i] = (int) (tr.kx / 1200.0 * w + x0);
			ky[i] = (int) (y0 - tr.ky / 500.0 * h);
		}

		//repaint();
	}

	public void createActions() {
		int shortcutKeyMask = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();
		newAction = new newActionClass("����1", KeyStroke.getKeyStroke(
				KeyEvent.VK_N, shortcutKeyMask));//CTRL+N��ݼ�
		openAction = new openActionClass("����2", KeyStroke.getKeyStroke(
				KeyEvent.VK_O, shortcutKeyMask));//CTRL+O��ݼ�
	}
   /**
    * ��Ӳ˵�
    */
	public void addMenus() {
		fileMenu = new JMenu("operation");
		fileMenu.add(new JMenuItem(newAction));
		fileMenu.add(new JMenuItem(openAction));
		mainMenuBar.add(fileMenu);
		setJMenuBar(mainMenuBar);
	}
    //�������������ʲôʱ�����
	public void paint(Graphics g) {
		
		super.paint(g);
		LogB.i("paint ����ִ��");
		g.setFont(font);
		g.setColor(Color.red);
		g.drawString("true", 700, 80);
		g.setColor(Color.green);
		g.drawString("Measured", 700, 100);
		g.setColor(Color.blue);
		g.drawString("Kalman", 700, 120);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(timeUsed) + "ms", 700, 140);

		g.setColor(Color.white);
		g.drawLine(x0, y0, w, y0);
		g.drawLine(x0, y0, x0, y0 - h);
        //��ʵ�켣
		g.setColor(Color.red);
		g.drawPolyline(x, y, resultCount);
        //�����켣
		g.setColor(Color.green);
		g.drawPolyline(nx, ny, resultCount);
        //������
		g.setColor(Color.blue);
		g.drawPolyline(kx, ky, resultCount);
	}

	public class newActionClass extends AbstractAction {
		public newActionClass(String text, KeyStroke shortcut) {
			super(text);
			putValue(ACCELERATOR_KEY, shortcut);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("New...");
		}
	}

	public class openActionClass extends AbstractAction {
		public openActionClass(String text, KeyStroke shortcut) {
			super(text);
			putValue(ACCELERATOR_KEY, shortcut);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("Open...");
		}
	}

	public static void main(String args[]) {
		new Test();
	}
}