/*
 * This file is part of Encom. **ENCOM FUCK OTHER SVN**
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.poeta;

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.zone.ZoneName;

/****/
/** Author Rinzler (Encom)
/****/

public class _1100Kalios_Call extends QuestHandler
{
	private final static int questId = 1100;
	
	public _1100Kalios_Call() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(203067).addOnTalkEvent(questId);
		qe.registerOnEnterZone(ZoneName.get("AKARIOS_VILLAGE_210010000"), questId);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
			return false;
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		if (targetId != 203067)
			return false;
		if (qs.getStatus() == QuestStatus.START) {
			if (env.getDialog() == QuestDialog.START_DIALOG) {
				qs.setQuestVar(1);
				qs.setStatus(QuestStatus.REWARD);
				updateQuestStatus(env);
				return sendQuestDialog(env, 1011);
			} else {
				return sendQuestStartDialog(env);
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (env.getDialogId() == 23) {
				int[] ids = {1001, 1002, 1003, 1004, 1005};
				for (int id: ids) {
					QuestEngine.getInstance().onEnterZoneMissionEnd(new QuestEnv(env.getVisibleObject(), env.getPlayer(), id, env.getDialogId()));
				}
			}
			return sendQuestEndDialog(env);
		}
		TeleportService2.teleportTo(player, 110010000, 1463.987f, 1430.4983f, 572.8723f, (byte) 54, TeleportAnimation.FIRE_ANIMATION);
		return false;
	}
	
	@Override
	public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
		return defaultOnEnterZoneEvent(env, zoneName, ZoneName.get("AKARIOS_VILLAGE_210010000"));
	}
}