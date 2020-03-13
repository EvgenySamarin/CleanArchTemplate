/**
 * Representation of UI layer, contain activities, fragments, dialogs etc. All of ui components
 * <p>Hierarchy is represented following template:</p>
 * <ol><p>Activity package - defines activity class</p>
 *     <li>Fragment package - defines fragment classes which would be used into related activity
 *          <ul>
 *              <li>Adapter package - defines adapters which would be used into related fragments</li>
 *              <li>Callback package - defines different callbacks which would be used to interact
 *              between fragments and other ui elements (adapters, activity, fragments etc). Its
 *              package is optional</li>
 *          </ul>
 *     </li>
 *     <li>Dialog package - defines dialogs to used into different ui elements</li>
 * </ol>
 *
 * @author EvgenySamarin
 * @since 20190905 v3
 */
package com.example.cleanarchtemplate.presentation.ui;