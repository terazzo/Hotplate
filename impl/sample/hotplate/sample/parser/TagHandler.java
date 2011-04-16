package sample.hotplate.sample.parser;

import java.util.List;

import sample.hotplate.sample.prototype.SimpleTemplatePrototype;

/**
 * �p�[�X���Ƀ^�O����舵���ׂ̃C���^�t�F�[�X
 */
public interface TagHandler {
    /** @return ���̃n���h���������^�O���̈ꗗ��߂��B */
    String[] tagNames();
    /**
     * @return �V���O���^�O���󂯎��ꍇtrue�A�󂯎��Ȃ��ꍇ��false��߂��B
     * @param tagName �^�O��
     */
    boolean requireSingleTag(String tagName);
    /**
     * @return �R���e�i�^�O���󂯎��ꍇtrue�A�󂯎��Ȃ��ꍇ��false��߂��B
     * @param tagName �^�O��
     */
    boolean requireContainerTag(String tagName);
    /**
     * @return �V���O���^�O�̒�`���󂯎���ă^�O�p��SimpleTemplatePrototype��߂��B
     * @param tagName �^�O��
     * @param attributes �����̃��X�g
     */
    SimpleTemplatePrototype handleSingleTag(String tagName, List<Attribute> attributes);
    /**
     * @return �R���e�i�^�O�̒�`���󂯎���ă^�O�p��SimpleTemplatePrototype��߂��B
     * @param tagName �^�O��
     * @param attributes �����̃��X�g
     * @param elements �^�O�̒��g���܂�SimpleTemplatePrototype��List
     */
    SimpleTemplatePrototype handleContainerTag(String tagName, List<Attribute> attributes, List<SimpleTemplatePrototype> elements);
}
