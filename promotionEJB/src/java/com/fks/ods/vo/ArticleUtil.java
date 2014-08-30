/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ods.vo;

import com.eks.ods.article.vo.ArticleVO;
import com.fks.promotion.vo.ArticleMCVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krutij
 */
public class ArticleUtil {

    public static List<ArticleMCVO> getArticleSearchUtil(List<ArticleVO> articleList) {
        List<ArticleMCVO> list = new ArrayList<ArticleMCVO>();
        if (articleList != null && articleList.size() > 0) {
            ArticleMCVO articleMCVO = null;
            for (ArticleVO vo : articleList) {
                articleMCVO = new ArticleMCVO();
                articleMCVO.setArticleCode(vo.getArticleCode());
                articleMCVO.setArticleDesc(vo.getArticleDesc());
                articleMCVO.setBrand(vo.getBrand());
                articleMCVO.setMcCode(vo.getMcCode());
                articleMCVO.setMcDesc(vo.getMcDesc());
                articleMCVO.setPrice(vo.getMrp());
                articleMCVO.setSeasonCode(vo.getSeasonCode());
                list.add(articleMCVO);
            }
        }


        return list;
    }
}
