package pororo.com.automata;

import java.awt.Container;
import java.awt.event.KeyEvent;

import org.havi.ui.HState;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.log.Log;

import com.mobience.automata.Automata;
import com.mobience.automata.CharacterListener;

public class AutoMataContainer extends Container implements Constant, CharacterListener
{
	private int KEY_X = 0;
	private int KEY_Y = 0;
	private int KEY_W = 348;
	private int KEY_H = 334;

	private int TEXT_X = 427 - 330;
	private int TEXT_Y = 152 - 73;
	private int TEXT_W = 160;
	private int TEXT_H = 46;

	public final byte STATE_INPUT = 0;
	public final byte STATE_OK = 1;

	public KeyboardComponent keyComponent;
	private TextComponent textComponent;

	private TextListener textListener;

	private Automata mata;

	public byte conState = 0;

	public AutoMataContainer()
	{
	}

	public void addListener(TextListener tl)
	{
		this.textListener = tl;
	}

	public void initContainer()
	{
		StateValue.isAutomataVisible = true;

		if (textComponent == null)
		{
			textComponent = new TextComponent();
			textComponent.setBounds(TEXT_X, TEXT_Y, TEXT_W, TEXT_H);
			add(textComponent);
		}
		textComponent.hEntry = null;
		textComponent.autoText = "";
		textComponent.setEntry(0, 0, TEXT_W, TEXT_H - 14, 6);

		if (keyComponent == null)
		{
			keyComponent = new KeyboardComponent();
			keyComponent.setBounds(KEY_X, KEY_Y, KEY_W, KEY_H);
			add(keyComponent);
		}
		//		keyComponent.addKeyListener(this);
		keyComponent.requestFocus();

		setVisible(true);

		mata = com.mobience.automata.AutomataManager.createInstance();
		mata.resetStatus(true);
		mata.setCharacterListener(this);

		//  131209 천지인 모드 적용 추가 (by 김정곤 )
		
		Log.debug("================ mata.getKeyInputSystem() : " + mata.getKeyInputSystem());
		mata.setKeyInputSystem(Automata.KEY_SYSTEM_CHUNJIIN);

		initRemocon();

	}

	public void destroy()
	{
		StateValue.isAutomataVisible = false;
		textComponent.autoText = null;

		mata.removeCharacterListener();
		com.mobience.automata.AutomataManager.destroyInstance(mata);
		mata = null;

		keyComponent.destroy();
		//		keyComponent.removeKeyListener(this);

		textComponent.destroy();
		textListener = null;
		setVisible(false);
		removeAll();

		keyComponent = null;
		textComponent = null;
	}

	/**
	 * automata 좌표를 설정한다.
	 */
	public void initRemocon()
	{
		keyComponent.XPos = 0;
		keyComponent.YPos = 0;
		keyComponent.cursorPos = 0;
		keyComponent.automata_Mode = Automata.KOREAN_MODE;
		//		mata.setCurrentMode(keyComponent.automata_Mode);
		//		setLocation(x, y);
	}

	public void keyPressed(KeyEvent e)
	{
		System.out.println("automata keypressed~");
		int keyCode = e.getKeyCode();

		if (SceneManager.autoBool)
			return;

		//		Log.trace(this, "========Keycode========== : "+keyCode);
		if (keyComponent.state == keyComponent.ID_ERROR)
			keyComponent.state = keyComponent.ID_INPUT;
		switch (keyCode)
		{
			case KeyEvent.VK_0:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 1;
				keyComponent.YPos = 3;
				break;
			case KeyEvent.VK_1:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 0;
				keyComponent.YPos = 0;
				break;
			case KeyEvent.VK_2:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 1;
				keyComponent.YPos = 0;
				break;
			case KeyEvent.VK_3:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 2;
				keyComponent.YPos = 0;
				break;
			case KeyEvent.VK_4:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 0;
				keyComponent.YPos = 1;
				break;
			case KeyEvent.VK_5:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 1;
				keyComponent.YPos = 1;
				break;
			case KeyEvent.VK_6:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 2;
				keyComponent.YPos = 1;
				break;
			case KeyEvent.VK_7:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 0;
				keyComponent.YPos = 2;
				break;
			case KeyEvent.VK_8:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 1;
				keyComponent.YPos = 2;
				break;
			case KeyEvent.VK_9:
				mata.keyPressed(keyCode);
				keyComponent.XPos = 2;
				keyComponent.YPos = 2;
				break;
			case Constant.KEY_Space:
				textComponent.autoText += " ";
				mata.resetStatus(true);
				keyComponent.XPos = 2;
				keyComponent.YPos = 3;
				break;
			case Constant.KEY_InputMode:
				switch (keyComponent.automata_Mode)
				{
					case Automata.KOREAN_MODE:
						keyComponent.automata_Mode = Automata.ENGLISH_LARGE_MODE;
						break;
					case Automata.ENGLISH_LARGE_MODE:
						keyComponent.automata_Mode = Automata.ENGLISH_SMALL_MODE;
						break;
					case Automata.ENGLISH_SMALL_MODE:
						keyComponent.automata_Mode = Automata.NUMERIC_MODE;
						break;
					case Automata.NUMERIC_MODE:
						keyComponent.automata_Mode = Automata.KOREAN_MODE;
						break;
				}
				mata.setCurrentMode(keyComponent.automata_Mode);
				mata.resetStatus(true);
				keyComponent.XPos = 0;
				keyComponent.YPos = 4;
				break;
			case Constant.KEY_Remove:
				mata.keyPressed(Constant.KEY_Remove);
				mata.resetStatus(true);
				keyComponent.XPos = 2;
				keyComponent.YPos = 4;
				break;

			case KeyEvent.VK_RIGHT:
				keyComponent.XPos = keyComponent.XPos == 0 ? 1 : keyComponent.XPos == 1 ? 2 : 0;
				break;
			case KeyEvent.VK_LEFT:
				keyComponent.XPos = keyComponent.XPos == 0 ? 2 : keyComponent.XPos == 1 ? 0 : 1;
				break;
			case KeyEvent.VK_UP:
				keyComponent.YPos--;
				if (keyComponent.YPos < 0)
					keyComponent.YPos = 4;
				break;
			case KeyEvent.VK_DOWN:
				keyComponent.YPos++;
				if (keyComponent.YPos >= 5)
					keyComponent.YPos = 0;
				break;
			case KeyEvent.VK_F9:
				mata.keyPressed(keyCode);
				//			mata.resetStatus(true);
				keyComponent.XPos = 0;
				keyComponent.YPos = 3;

				//			mata.setCurrentMode(Automata.SYMBOL_MODE);
				//			mata.keyPressed(51);
				//			mata.setCurrentMode(keyComponent.automata_Mode);
				//			//    		 autoText += "-";
				//			mata.resetStatus(true);
				break;
			case Constant.KEY_PREV:
				//			sceneChange();
				//			destroy();
				SceneManager.autoBool = true;
				break;
			case KeyEvent.VK_ENTER:
				keyCode = keyComponent.YPos * 3 + keyComponent.XPos + 49;

				if (keyCode == 59)
				{ // 0번 클릭시
					keyCode = 48;
				}

				if (keyCode == 58)
				{ // 기호 클릭시
					//				mata.keyPressed(keyCode);
					e.setKeyCode(KeyEvent.VK_F9);
					this.keyPressed(e);
				}
				else if (keyCode == 60)
				{ // 띄우기
					e.setKeyCode(Constant.KEY_Space);
					this.keyPressed(e);
				}
				else if (keyCode == 61)
				{ // 한영특
					e.setKeyCode(Constant.KEY_InputMode);
					this.keyPressed(e);
				}
				else if (keyCode == 62) // 입력완료
				{
					textListener.receiveString(textComponent.getText());
					//				sceneChange();
					//				destroy();
					conState = STATE_OK;
					SceneManager.autoBool = true;
				}
				else if (keyCode == 63)
				{ // 삭제
					e.setKeyCode(Constant.KEY_Remove);
					this.keyPressed(e);
				}
				else
				{
					// 숫자키 키코드에 맞게 순서대로 코드를 넣어준다.
					// (키배열에 따라 49부터 57까지 들어가게 되어있음. 그리고 0키를 누르면 59를 48로 치환함..)
					mata.keyPressed(keyCode);
				}
		}
	}

	public void sceneChange()
	{
		// 입력완료 시 	포커스를 씬으로 돌려준다.
		Container parCotainer = getParent();

		if (parCotainer != null)
		{
			parCotainer.remove(this);
			SceneManager.getInstance().requestFocus();
		}
	}

	public void keyReleased(KeyEvent e)
	{

	}

	public void keyTyped(KeyEvent e)
	{

	}

	public void characterEvent(int eventType, char c)
	{
		switch (eventType)
		{
			case Automata.EVENT_PUT: //초성 : 입력된 값이 없으면 추가하고 있으면 변경한다.
				if (textComponent.maxCount >= keyComponent.cursorPos)
				{
					textComponent.strCurrent = String.valueOf(c);
					if (keyComponent.cursorPos == textComponent.autoText.length())
					{
						textComponent.autoText += textComponent.strCurrent;
					}
					else
					{
						String imsi = textComponent.autoText; //
						textComponent.autoText = imsi.substring(0, imsi.length() - 1) + textComponent.strCurrent;
					}
				}
				break;
			case Automata.EVENT_DELETE: //삭제 : 현재 커서에 해당하는 텍스트를 삭제한다.
				if (textComponent.autoText.length() > 0)
				{
					if (textComponent.autoText.length() == keyComponent.cursorPos)
						keyComponent.cursorPos--;
					textComponent.autoText = textComponent.autoText.substring(0, textComponent.autoText.length() - 1);
				}
				break;
			case Automata.EVENT_NEXT: //
				keyComponent.cursorPos = textComponent.autoText.length();
				break;
			case Automata.EVENT_UPDATE: // 최종
				keyComponent.repaint();
				break;
		}

		// 11/11 한글 입력을 위해 글자 변경 시 entry에 텍스트를 셋팅해준다.
		if (textComponent.hEntry != null)
		{
			textComponent.hEntry.setTextContent(textComponent.autoText, HState.NORMAL_STATE);
			textComponent.hEntry.repaint();
		}
	}

	public interface TextListener
	{
		public void receiveString(String str);
	}
}
