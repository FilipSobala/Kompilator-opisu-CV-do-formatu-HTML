package org.example.cv.antlr;// Generated from CvDsl.g4 by ANTLR 4.13.2

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CvDslParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CvDslVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CvDslParser#cv_document}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCv_document(CvDslParser.Cv_documentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#import_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_stmt(CvDslParser.Import_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#config_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfig_block(CvDslParser.Config_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection(CvDslParser.SectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContent(CvDslParser.ContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(CvDslParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#list_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_field(CvDslParser.List_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#bullet_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBullet_list(CvDslParser.Bullet_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#object_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject_list(CvDslParser.Object_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#object_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject_block(CvDslParser.Object_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CvDslParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(CvDslParser.ValueContext ctx);
}