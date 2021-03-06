/*
 * DataFrameType.java
 *
 * Copyright (C) 2009-11 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.common.filetypes;

import org.rstudio.core.client.files.FileSystemItem;
import org.rstudio.studio.client.application.events.EventBus;

public class DataFrameType extends EditableFileType
{
   public DataFrameType()
   {
      super("r_dataframe", "R Data Frame",
            FileIconResources.INSTANCE.iconRdata());
   }

   @Override
   public void openFile(FileSystemItem file, EventBus eventBus)
   {
      assert false : "DataFrameType doesn't operate on filesystem files";
   }
}
