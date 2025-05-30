/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.index.tree.internal.nestedpages.query;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;

/**
 * Filters the child pages of a specified parent page. This filter works with the named <strong>native SQL</strong>
 * queries declared in the {@code queries.hbm.xml} mapping file.
 * 
 * @version $Id$
 * @since 8.3RC1
 * @since 7.4.5
 */
@Component
@Named("childPage/nestedPages")
@Singleton
public class ChildPageFilter extends AbstractNestedPageFilter
{
    @Override
    protected String filterNestedPagesStatement(String statement)
    {
        // The constraint is different depending on whether we filter a native SQL query or an HQL query.
        String constraint =
            statement.indexOf("XWS_REFERENCE") < 0 ? "space.parent = :parent " : "XWS_PARENT = :parent ";
        return insertWhereConstraint(statement, constraint);
    }

    @Override
    protected String filterTerminalPagesStatement(String statement)
    {
        return statement + " and doc.XWD_WEB = :parent ";
    }
}
