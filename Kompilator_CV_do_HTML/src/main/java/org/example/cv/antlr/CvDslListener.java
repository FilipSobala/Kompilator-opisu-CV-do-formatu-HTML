// Generated from gramatyka/CvDsl.g4 by ANTLR 4.13.2
package org.example.cv.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CvDslParser}.
 */
public interface CvDslListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CvDslParser#cv_document}.
	 * @param ctx the parse tree
	 */
	void enterCv_document(CvDslParser.Cv_documentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#cv_document}.
	 * @param ctx the parse tree
	 */
	void exitCv_document(CvDslParser.Cv_documentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void enterImport_stmt(CvDslParser.Import_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void exitImport_stmt(CvDslParser.Import_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#config_block}.
	 * @param ctx the parse tree
	 */
	void enterConfig_block(CvDslParser.Config_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#config_block}.
	 * @param ctx the parse tree
	 */
	void exitConfig_block(CvDslParser.Config_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(CvDslParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(CvDslParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#content}.
	 * @param ctx the parse tree
	 */
	void enterContent(CvDslParser.ContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#content}.
	 * @param ctx the parse tree
	 */
	void exitContent(CvDslParser.ContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(CvDslParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(CvDslParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#list_field}.
	 * @param ctx the parse tree
	 */
	void enterList_field(CvDslParser.List_fieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#list_field}.
	 * @param ctx the parse tree
	 */
	void exitList_field(CvDslParser.List_fieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#bullet_list}.
	 * @param ctx the parse tree
	 */
	void enterBullet_list(CvDslParser.Bullet_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#bullet_list}.
	 * @param ctx the parse tree
	 */
	void exitBullet_list(CvDslParser.Bullet_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#object_list}.
	 * @param ctx the parse tree
	 */
	void enterObject_list(CvDslParser.Object_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#object_list}.
	 * @param ctx the parse tree
	 */
	void exitObject_list(CvDslParser.Object_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#object_block}.
	 * @param ctx the parse tree
	 */
	void enterObject_block(CvDslParser.Object_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#object_block}.
	 * @param ctx the parse tree
	 */
	void exitObject_block(CvDslParser.Object_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CvDslParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(CvDslParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CvDslParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(CvDslParser.ValueContext ctx);
}