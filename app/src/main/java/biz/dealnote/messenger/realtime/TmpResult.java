package biz.dealnote.messenger.realtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import biz.dealnote.messenger.api.model.Identificable;
import biz.dealnote.messenger.api.model.VKApiMessage;
import biz.dealnote.messenger.model.Message;

import static biz.dealnote.messenger.util.Objects.nonNull;

/**
 * Created by admin on 11.04.2017.
 * phoenix
 */
public class TmpResult {

    private final List<Msg> data;

    private final int accountId;

    private final int id;

    public TmpResult(int id, int accountId, int capacity) {
        this.data = new ArrayList<>(capacity);
        this.id = id;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public Msg prepare(int id) {
        for (Msg m : data) {
            if (m.id == id) {
                return m;
            }
        }

        return add(id);
    }

    public TmpResult appendDtos(List<VKApiMessage> dtos) {
        for (VKApiMessage dto : dtos) {
            prepare(dto.id).setDto(dto);
        }

        return this;
    }

    public TmpResult appendModel(List<Message> messages) {
        for (Message m : messages) {
            prepare(m.getId()).setMessage(m);
        }

        return this;
    }

    public List<VKApiMessage> collectDtos() {
        List<VKApiMessage> dtos = new ArrayList<>(data.size());
        for (Msg msg : data) {
            if (nonNull(msg.dto)) {
                dtos.add(msg.dto);
            }
        }

        return dtos;
    }

    public List<Msg> getData() {
        return data;
    }

    public Msg add(int id) {
        Msg msg = new Msg(id);
        data.add(msg);
        return msg;
    }

    public TmpResult setMissingIds(Collection<Integer> ids) {
        for (Msg msg : data) {
            msg.setAlreadyExists(!ids.contains(msg.id));
        }

        return this;
    }

    public List<Integer> getAllIds() {
        if (data.isEmpty()) {
            return Collections.emptyList();
        }

        if (data.size() == 1) {
            return Collections.singletonList(data.get(0).id);
        }

        List<Integer> ids = new ArrayList<>(data.size());
        for (Msg msg : data) {
            ids.add(msg.id);
        }

        return ids;
    }

    public int getAccountId() {
        return accountId;
    }

    public static class Msg implements Identificable {

        private final int id;

        private boolean alreadyExists;

        private Message message;

        private VKApiMessage dto;

        Msg(int id) {
            this.id = id;
        }

        public Msg setDto(VKApiMessage dto) {
            this.dto = dto;
            return this;
        }

        public VKApiMessage getDto() {
            return dto;
        }

        public Msg setMessage(Message message) {
            this.message = message;
            return this;
        }

        public Message getMessage() {
            return message;
        }

        public Msg setAlreadyExists(boolean alreadyExists) {
            this.alreadyExists = alreadyExists;
            return this;
        }

        boolean isAlreadyExists() {
            return alreadyExists;
        }

        @Override
        public int getId() {
            return id;
        }
    }

    @Override
    public String toString() {
        return "[" + id + "] -> " + data.toString();
    }
}