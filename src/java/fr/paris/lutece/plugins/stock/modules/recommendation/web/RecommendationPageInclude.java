/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */


package fr.paris.lutece.plugins.stock.modules.recommendation.web;

import fr.paris.lutece.plugins.stock.modules.recommendation.business.RecommendedProduct;
import fr.paris.lutece.plugins.stock.modules.recommendation.service.StockRecommendationService;
import fr.paris.lutece.portal.service.content.PageData;
import fr.paris.lutece.portal.service.includes.PageInclude;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.util.html.HtmlTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.mahout.cf.taste.common.TasteException;

/**
 * RecommendationPageInclude
 */
public class RecommendationPageInclude implements PageInclude
{
    private static final String TEMPLATE_PAGE_INCLUDE = "/skin/plugins/stock/modules/recommendation/page_include.html";
    private static final String MARK_RECOMMENDATIONS = "recommendations";
    /**
     * {@inheritDoc }
     */
    @Override
    public void fillTemplate( Map<String, Object> rootModel, PageData data, int nMode, HttpServletRequest request )
    {
        String strContent;
        List<RecommendedProduct> listProducts = null;
        try
        {
            String strUserName = RecommendationApp.getUsername( request );
            listProducts = StockRecommendationService.instance( ).getRecommendedProducts( strUserName );
            Map<String, Object> model = new HashMap<>();
            model.put( RecommendationApp.MARK_PRODUCTS_LIST, listProducts );
            model.put( RecommendationApp.MARK_PRODUCT_LINK_URL, RecommendationApp.PRODUCT_LINK_URL );
            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_PAGE_INCLUDE, LocaleService.getDefault(), model );
            strContent = template.getHtml();
        }
        catch( TasteException ex )
        {
            // User not found
            strContent = "User not found";
        }
        catch( UserNotSignedException ex )
        {
            // User not signed
            strContent = "User not signed";
        }
        
        rootModel.put( MARK_RECOMMENDATIONS, strContent );
    }

}