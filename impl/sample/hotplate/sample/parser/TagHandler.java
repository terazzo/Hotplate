package sample.hotplate.sample.parser;

import java.util.List;

import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

/**
 * パース時にタグを取り扱う為のインタフェース
 */
public interface TagHandler {
    /** @return このハンドラが扱うタグ名の一覧を戻す。 */
    String[] tagNames();
    /**
     * @return シングルタグを受け取る場合true、受け取らない場合はfalseを戻す。
     * @param tagName タグ名
     */
    boolean requireSingleTag(String tagName);
    /**
     * @return コンテナタグを受け取る場合true、受け取らない場合はfalseを戻す。
     * @param tagName タグ名
     */
    boolean requireContainerTag(String tagName);
    /**
     * @return シングルタグの定義を受け取ってタグ用のSimpleTemplatePrototypeを戻す。
     * @param tagName タグ名
     * @param attributes 属性のリスト
     */
    SimpleTemplatePrototype handleSingleTag(String tagName, List<Attribute> attributes);
    /**
     * @return コンテナタグの定義を受け取ってタグ用のSimpleTemplatePrototypeを戻す。
     * @param tagName タグ名
     * @param attributes 属性のリスト
     * @param elements タグの中身を含むSimpleTemplatePrototypeのList
     */
    SimpleTemplatePrototype handleContainerTag(String tagName, List<Attribute> attributes, List<SimpleTemplatePrototype> elements);
}
