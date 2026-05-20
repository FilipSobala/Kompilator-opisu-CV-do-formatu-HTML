// Generated from CvDsl.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class CvDslParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T_START=1, T_END=2, T_CONFIG=3, T_SECTION=4, T_IMPORT=5, T_PRESENT=6, 
		T_BOOLEAN=7, T_LBRACE=8, T_RBRACE=9, T_LSQUARE=10, T_RSQUARE=11, T_COMMA=12, 
		T_DASH=13, T_MULTILINE=14, T_STRING=15, T_URL=16, T_DATE=17, T_EMAIL=18, 
		T_PHONE=19, T_NUMBER=20, T_KEY=21, T_LABEL=22, T_COMMENT=23, T_BLOCK_COMM=24, 
		WS=25;
	public static final int
		RULE_cv_document = 0, RULE_import_stmt = 1, RULE_config_block = 2, RULE_section = 3, 
		RULE_content = 4, RULE_pair = 5, RULE_list_field = 6, RULE_bullet_list = 7, 
		RULE_object_list = 8, RULE_object_block = 9, RULE_value = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"cv_document", "import_stmt", "config_block", "section", "content", "pair", 
			"list_field", "bullet_list", "object_list", "object_block", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'CV_START'", "'CV_END'", "'CONFIG'", "'SECTION'", "'IMPORT'", 
			null, null, "'{'", "'}'", "'['", "']'", "','", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "T_START", "T_END", "T_CONFIG", "T_SECTION", "T_IMPORT", "T_PRESENT", 
			"T_BOOLEAN", "T_LBRACE", "T_RBRACE", "T_LSQUARE", "T_RSQUARE", "T_COMMA", 
			"T_DASH", "T_MULTILINE", "T_STRING", "T_URL", "T_DATE", "T_EMAIL", "T_PHONE", 
			"T_NUMBER", "T_KEY", "T_LABEL", "T_COMMENT", "T_BLOCK_COMM", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CvDsl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CvDslParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Cv_documentContext extends ParserRuleContext {
		public TerminalNode T_START() { return getToken(CvDslParser.T_START, 0); }
		public TerminalNode T_END() { return getToken(CvDslParser.T_END, 0); }
		public TerminalNode EOF() { return getToken(CvDslParser.EOF, 0); }
		public List<Import_stmtContext> import_stmt() {
			return getRuleContexts(Import_stmtContext.class);
		}
		public Import_stmtContext import_stmt(int i) {
			return getRuleContext(Import_stmtContext.class,i);
		}
		public Config_blockContext config_block() {
			return getRuleContext(Config_blockContext.class,0);
		}
		public List<SectionContext> section() {
			return getRuleContexts(SectionContext.class);
		}
		public SectionContext section(int i) {
			return getRuleContext(SectionContext.class,i);
		}
		public Cv_documentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cv_document; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterCv_document(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitCv_document(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitCv_document(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Cv_documentContext cv_document() throws RecognitionException {
		Cv_documentContext _localctx = new Cv_documentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_cv_document);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			match(T_START);
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T_IMPORT) {
				{
				{
				setState(23);
				import_stmt();
				}
				}
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T_CONFIG) {
				{
				setState(29);
				config_block();
				}
			}

			setState(33); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(32);
				section();
				}
				}
				setState(35); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T_SECTION );
			setState(37);
			match(T_END);
			setState(38);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Import_stmtContext extends ParserRuleContext {
		public TerminalNode T_IMPORT() { return getToken(CvDslParser.T_IMPORT, 0); }
		public TerminalNode T_STRING() { return getToken(CvDslParser.T_STRING, 0); }
		public Import_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_import_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterImport_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitImport_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitImport_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Import_stmtContext import_stmt() throws RecognitionException {
		Import_stmtContext _localctx = new Import_stmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_import_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(T_IMPORT);
			setState(41);
			match(T_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Config_blockContext extends ParserRuleContext {
		public TerminalNode T_CONFIG() { return getToken(CvDslParser.T_CONFIG, 0); }
		public TerminalNode T_LBRACE() { return getToken(CvDslParser.T_LBRACE, 0); }
		public TerminalNode T_RBRACE() { return getToken(CvDslParser.T_RBRACE, 0); }
		public List<PairContext> pair() {
			return getRuleContexts(PairContext.class);
		}
		public PairContext pair(int i) {
			return getRuleContext(PairContext.class,i);
		}
		public Config_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_config_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterConfig_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitConfig_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitConfig_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Config_blockContext config_block() throws RecognitionException {
		Config_blockContext _localctx = new Config_blockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_config_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(T_CONFIG);
			setState(44);
			match(T_LBRACE);
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T_KEY) {
				{
				{
				setState(45);
				pair();
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			match(T_RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SectionContext extends ParserRuleContext {
		public TerminalNode T_SECTION() { return getToken(CvDslParser.T_SECTION, 0); }
		public TerminalNode T_LABEL() { return getToken(CvDslParser.T_LABEL, 0); }
		public TerminalNode T_LBRACE() { return getToken(CvDslParser.T_LBRACE, 0); }
		public TerminalNode T_RBRACE() { return getToken(CvDslParser.T_RBRACE, 0); }
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public SectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(T_SECTION);
			setState(54);
			match(T_LABEL);
			setState(55);
			match(T_LBRACE);
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T_KEY) {
				{
				{
				setState(56);
				content();
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(62);
			match(T_RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContentContext extends ParserRuleContext {
		public PairContext pair() {
			return getRuleContext(PairContext.class,0);
		}
		public List_fieldContext list_field() {
			return getRuleContext(List_fieldContext.class,0);
		}
		public Object_listContext object_list() {
			return getRuleContext(Object_listContext.class,0);
		}
		public Bullet_listContext bullet_list() {
			return getRuleContext(Bullet_listContext.class,0);
		}
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_content);
		try {
			setState(68);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				pair();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				list_field();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				object_list();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				bullet_list();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PairContext extends ParserRuleContext {
		public TerminalNode T_KEY() { return getToken(CvDslParser.T_KEY, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public PairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterPair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitPair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairContext pair() throws RecognitionException {
		PairContext _localctx = new PairContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(T_KEY);
			setState(71);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class List_fieldContext extends ParserRuleContext {
		public TerminalNode T_KEY() { return getToken(CvDslParser.T_KEY, 0); }
		public TerminalNode T_LSQUARE() { return getToken(CvDslParser.T_LSQUARE, 0); }
		public TerminalNode T_RSQUARE() { return getToken(CvDslParser.T_RSQUARE, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> T_COMMA() { return getTokens(CvDslParser.T_COMMA); }
		public TerminalNode T_COMMA(int i) {
			return getToken(CvDslParser.T_COMMA, i);
		}
		public List_fieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterList_field(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitList_field(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitList_field(this);
			else return visitor.visitChildren(this);
		}
	}

	public final List_fieldContext list_field() throws RecognitionException {
		List_fieldContext _localctx = new List_fieldContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_list_field);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(T_KEY);
			setState(74);
			match(T_LSQUARE);
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2080960L) != 0)) {
				{
				setState(75);
				value();
				setState(80);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(76);
						match(T_COMMA);
						setState(77);
						value();
						}
						} 
					}
					setState(82);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T_COMMA) {
					{
					setState(83);
					match(T_COMMA);
					}
				}

				}
			}

			setState(88);
			match(T_RSQUARE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Bullet_listContext extends ParserRuleContext {
		public TerminalNode T_KEY() { return getToken(CvDslParser.T_KEY, 0); }
		public List<TerminalNode> T_DASH() { return getTokens(CvDslParser.T_DASH); }
		public TerminalNode T_DASH(int i) {
			return getToken(CvDslParser.T_DASH, i);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public Bullet_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bullet_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterBullet_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitBullet_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitBullet_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bullet_listContext bullet_list() throws RecognitionException {
		Bullet_listContext _localctx = new Bullet_listContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_bullet_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T_KEY);
			setState(93); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(91);
				match(T_DASH);
				setState(92);
				value();
				}
				}
				setState(95); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T_DASH );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Object_listContext extends ParserRuleContext {
		public TerminalNode T_KEY() { return getToken(CvDslParser.T_KEY, 0); }
		public TerminalNode T_LSQUARE() { return getToken(CvDslParser.T_LSQUARE, 0); }
		public TerminalNode T_RSQUARE() { return getToken(CvDslParser.T_RSQUARE, 0); }
		public List<Object_blockContext> object_block() {
			return getRuleContexts(Object_blockContext.class);
		}
		public Object_blockContext object_block(int i) {
			return getRuleContext(Object_blockContext.class,i);
		}
		public List<TerminalNode> T_COMMA() { return getTokens(CvDslParser.T_COMMA); }
		public TerminalNode T_COMMA(int i) {
			return getToken(CvDslParser.T_COMMA, i);
		}
		public Object_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterObject_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitObject_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitObject_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Object_listContext object_list() throws RecognitionException {
		Object_listContext _localctx = new Object_listContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_object_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T_KEY);
			setState(98);
			match(T_LSQUARE);
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T_LBRACE) {
				{
				setState(99);
				object_block();
				setState(104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(100);
						match(T_COMMA);
						setState(101);
						object_block();
						}
						} 
					}
					setState(106);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T_COMMA) {
					{
					setState(107);
					match(T_COMMA);
					}
				}

				}
			}

			setState(112);
			match(T_RSQUARE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Object_blockContext extends ParserRuleContext {
		public TerminalNode T_LBRACE() { return getToken(CvDslParser.T_LBRACE, 0); }
		public TerminalNode T_RBRACE() { return getToken(CvDslParser.T_RBRACE, 0); }
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public Object_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterObject_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitObject_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitObject_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Object_blockContext object_block() throws RecognitionException {
		Object_blockContext _localctx = new Object_blockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_object_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(T_LBRACE);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T_KEY) {
				{
				{
				setState(115);
				content();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
			match(T_RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode T_STRING() { return getToken(CvDslParser.T_STRING, 0); }
		public TerminalNode T_MULTILINE() { return getToken(CvDslParser.T_MULTILINE, 0); }
		public TerminalNode T_DATE() { return getToken(CvDslParser.T_DATE, 0); }
		public TerminalNode T_PRESENT() { return getToken(CvDslParser.T_PRESENT, 0); }
		public TerminalNode T_URL() { return getToken(CvDslParser.T_URL, 0); }
		public TerminalNode T_EMAIL() { return getToken(CvDslParser.T_EMAIL, 0); }
		public TerminalNode T_PHONE() { return getToken(CvDslParser.T_PHONE, 0); }
		public TerminalNode T_NUMBER() { return getToken(CvDslParser.T_NUMBER, 0); }
		public TerminalNode T_BOOLEAN() { return getToken(CvDslParser.T_BOOLEAN, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CvDslListener ) ((CvDslListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CvDslVisitor ) return ((CvDslVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2080960L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0019~\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0001\u0000\u0005"+
		"\u0000\u0019\b\u0000\n\u0000\f\u0000\u001c\t\u0000\u0001\u0000\u0003\u0000"+
		"\u001f\b\u0000\u0001\u0000\u0004\u0000\"\b\u0000\u000b\u0000\f\u0000#"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002/\b\u0002\n\u0002\f\u0002"+
		"2\t\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0005\u0003:\b\u0003\n\u0003\f\u0003=\t\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"E\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006O\b\u0006\n\u0006\f\u0006"+
		"R\t\u0006\u0001\u0006\u0003\u0006U\b\u0006\u0003\u0006W\b\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0004\u0007^\b"+
		"\u0007\u000b\u0007\f\u0007_\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\bg\b\b\n\b\f\bj\t\b\u0001\b\u0003\bm\b\b\u0003\bo\b\b\u0001\b\u0001\b"+
		"\u0001\t\u0001\t\u0005\tu\b\t\n\t\f\tx\t\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0000\u0000\u000b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0000\u0001\u0002\u0000\u0006\u0007\u000e\u0014\u0082\u0000"+
		"\u0016\u0001\u0000\u0000\u0000\u0002(\u0001\u0000\u0000\u0000\u0004+\u0001"+
		"\u0000\u0000\u0000\u00065\u0001\u0000\u0000\u0000\bD\u0001\u0000\u0000"+
		"\u0000\nF\u0001\u0000\u0000\u0000\fI\u0001\u0000\u0000\u0000\u000eZ\u0001"+
		"\u0000\u0000\u0000\u0010a\u0001\u0000\u0000\u0000\u0012r\u0001\u0000\u0000"+
		"\u0000\u0014{\u0001\u0000\u0000\u0000\u0016\u001a\u0005\u0001\u0000\u0000"+
		"\u0017\u0019\u0003\u0002\u0001\u0000\u0018\u0017\u0001\u0000\u0000\u0000"+
		"\u0019\u001c\u0001\u0000\u0000\u0000\u001a\u0018\u0001\u0000\u0000\u0000"+
		"\u001a\u001b\u0001\u0000\u0000\u0000\u001b\u001e\u0001\u0000\u0000\u0000"+
		"\u001c\u001a\u0001\u0000\u0000\u0000\u001d\u001f\u0003\u0004\u0002\u0000"+
		"\u001e\u001d\u0001\u0000\u0000\u0000\u001e\u001f\u0001\u0000\u0000\u0000"+
		"\u001f!\u0001\u0000\u0000\u0000 \"\u0003\u0006\u0003\u0000! \u0001\u0000"+
		"\u0000\u0000\"#\u0001\u0000\u0000\u0000#!\u0001\u0000\u0000\u0000#$\u0001"+
		"\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0005\u0002\u0000\u0000"+
		"&\'\u0005\u0000\u0000\u0001\'\u0001\u0001\u0000\u0000\u0000()\u0005\u0005"+
		"\u0000\u0000)*\u0005\u000f\u0000\u0000*\u0003\u0001\u0000\u0000\u0000"+
		"+,\u0005\u0003\u0000\u0000,0\u0005\b\u0000\u0000-/\u0003\n\u0005\u0000"+
		".-\u0001\u0000\u0000\u0000/2\u0001\u0000\u0000\u00000.\u0001\u0000\u0000"+
		"\u000001\u0001\u0000\u0000\u000013\u0001\u0000\u0000\u000020\u0001\u0000"+
		"\u0000\u000034\u0005\t\u0000\u00004\u0005\u0001\u0000\u0000\u000056\u0005"+
		"\u0004\u0000\u000067\u0005\u0016\u0000\u00007;\u0005\b\u0000\u00008:\u0003"+
		"\b\u0004\u000098\u0001\u0000\u0000\u0000:=\u0001\u0000\u0000\u0000;9\u0001"+
		"\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<>\u0001\u0000\u0000\u0000"+
		"=;\u0001\u0000\u0000\u0000>?\u0005\t\u0000\u0000?\u0007\u0001\u0000\u0000"+
		"\u0000@E\u0003\n\u0005\u0000AE\u0003\f\u0006\u0000BE\u0003\u0010\b\u0000"+
		"CE\u0003\u000e\u0007\u0000D@\u0001\u0000\u0000\u0000DA\u0001\u0000\u0000"+
		"\u0000DB\u0001\u0000\u0000\u0000DC\u0001\u0000\u0000\u0000E\t\u0001\u0000"+
		"\u0000\u0000FG\u0005\u0015\u0000\u0000GH\u0003\u0014\n\u0000H\u000b\u0001"+
		"\u0000\u0000\u0000IJ\u0005\u0015\u0000\u0000JV\u0005\n\u0000\u0000KP\u0003"+
		"\u0014\n\u0000LM\u0005\f\u0000\u0000MO\u0003\u0014\n\u0000NL\u0001\u0000"+
		"\u0000\u0000OR\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000PQ\u0001"+
		"\u0000\u0000\u0000QT\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000"+
		"SU\u0005\f\u0000\u0000TS\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000"+
		"UW\u0001\u0000\u0000\u0000VK\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000"+
		"\u0000WX\u0001\u0000\u0000\u0000XY\u0005\u000b\u0000\u0000Y\r\u0001\u0000"+
		"\u0000\u0000Z]\u0005\u0015\u0000\u0000[\\\u0005\r\u0000\u0000\\^\u0003"+
		"\u0014\n\u0000][\u0001\u0000\u0000\u0000^_\u0001\u0000\u0000\u0000_]\u0001"+
		"\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`\u000f\u0001\u0000\u0000"+
		"\u0000ab\u0005\u0015\u0000\u0000bn\u0005\n\u0000\u0000ch\u0003\u0012\t"+
		"\u0000de\u0005\f\u0000\u0000eg\u0003\u0012\t\u0000fd\u0001\u0000\u0000"+
		"\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001\u0000"+
		"\u0000\u0000il\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000km\u0005"+
		"\f\u0000\u0000lk\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000mo\u0001"+
		"\u0000\u0000\u0000nc\u0001\u0000\u0000\u0000no\u0001\u0000\u0000\u0000"+
		"op\u0001\u0000\u0000\u0000pq\u0005\u000b\u0000\u0000q\u0011\u0001\u0000"+
		"\u0000\u0000rv\u0005\b\u0000\u0000su\u0003\b\u0004\u0000ts\u0001\u0000"+
		"\u0000\u0000ux\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000vw\u0001"+
		"\u0000\u0000\u0000wy\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000"+
		"yz\u0005\t\u0000\u0000z\u0013\u0001\u0000\u0000\u0000{|\u0007\u0000\u0000"+
		"\u0000|\u0015\u0001\u0000\u0000\u0000\u000e\u001a\u001e#0;DPTV_hlnv";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}