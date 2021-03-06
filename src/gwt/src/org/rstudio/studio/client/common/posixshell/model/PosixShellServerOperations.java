/*
 * PosixShellServerOperations.java
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
package org.rstudio.studio.client.common.posixshell.model;

import org.rstudio.studio.client.common.crypto.PublicKeyInfo;
import org.rstudio.studio.client.server.Void;
import org.rstudio.studio.client.server.ServerRequestCallback;

public interface PosixShellServerOperations
{
   void startPosixShell(int width,
                        int maxLines,
                        ServerRequestCallback<PublicKeyInfo> requestCallback);
   
   void interruptPosixShell(ServerRequestCallback<Void> requestCallback);
   
   void sendInputToPosixShell(String input, 
                              ServerRequestCallback<Void> requestCallback);
   
   void terminatePosixShell(ServerRequestCallback<Void> requestCallback);

}
