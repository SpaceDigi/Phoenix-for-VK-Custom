package biz.dealnote.messenger.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import biz.dealnote.messenger.R;
import biz.dealnote.messenger.domain.IGroupSettingsInteractor;
import biz.dealnote.messenger.domain.InteractorFactory;
import biz.dealnote.messenger.model.Banned;
import biz.dealnote.messenger.model.BlockReason;
import biz.dealnote.messenger.model.IdOption;
import biz.dealnote.messenger.model.User;
import biz.dealnote.messenger.mvp.presenter.base.AccountDependencyPresenter;
import biz.dealnote.messenger.mvp.view.ICommunityBanEditView;
import biz.dealnote.messenger.util.Logger;
import biz.dealnote.messenger.util.RxUtils;
import biz.dealnote.messenger.util.Utils;
import biz.dealnote.mvp.reflect.OnGuiCreated;

import static biz.dealnote.messenger.util.Objects.isNull;
import static biz.dealnote.messenger.util.Objects.nonNull;
import static biz.dealnote.messenger.util.Utils.getCauseIfRuntime;

/**
 * Created by admin on 17.06.2017.
 * phoenix
 */
public class CommunityBanEditPresenter extends AccountDependencyPresenter<ICommunityBanEditView> {

    private static final String TAG = CommunityBanEditPresenter.class.getSimpleName();

    private final int groupId;

    private final Banned banned;

    private final ArrayList<User> users;

    private int index;

    private BlockFor blockFor;

    private int reason;

    private String comment;

    private boolean showCommentToUser;

    private final IGroupSettingsInteractor interactor;

    public CommunityBanEditPresenter(int accountId, int groupId, Banned banned, @Nullable Bundle savedInstanceState) {
        super(accountId, savedInstanceState);
        this.groupId = groupId;
        this.banned = banned;
        this.users = Utils.singletonArrayList(banned.getUser());

        Banned.Info info = banned.getInfo();

        this.blockFor = new BlockFor(info.getEndDate());
        this.reason = info.getReason();
        this.comment = info.getComment();
        this.showCommentToUser = info.isCommentVisible();
        this.index = 0;
        this.interactor = InteractorFactory.createGroupSettingsInteractor();
    }

    public CommunityBanEditPresenter(int accountId, int groupId, ArrayList<User> users, @Nullable Bundle savedInstanceState) {
        super(accountId, savedInstanceState);
        this.groupId = groupId;
        this.banned = null;
        this.users = users;
        this.index = 0;
        this.blockFor = new BlockFor(BlockFor.FOREVER); // by default
        this.reason = BlockReason.OTHER;
        this.interactor = InteractorFactory.createGroupSettingsInteractor();
    }

    private User currentUser() {
        return users.get(index);
    }

    @OnGuiCreated
    private void resolveCommentViews(){
        if(isGuiReady()){
            getView().diplayComment(comment);
            getView().setShowCommentChecked(showCommentToUser);
        }
    }

    @OnGuiCreated
    private void resolveBanStatusView(){
        if(isGuiReady()){
            if (nonNull(banned)) {
                getView().displayBanStatus(banned.getAdmin().getId(), banned.getAdmin().getFullName(), banned.getInfo().getEndDate());
            }
        }
    }

    @OnGuiCreated
    private void resolveUserInfoViews(){
        if(isGuiReady()){
            getView().displayUserInfo(currentUser());
        }
    }

    @OnGuiCreated
    private void resolveBlockForView() {
        if (isGuiReady()) {
            String blockForText;

            switch (blockFor.type) {
                case BlockFor.FOREVER:
                    blockForText = getString(R.string.block_for_forever);
                    break;

                case BlockFor.YEAR:
                    blockForText = getString(R.string.block_for_year);
                    break;

                case BlockFor.MONTH:
                    blockForText = getString(R.string.block_for_month);
                    break;

                case BlockFor.WEEK:
                    blockForText = getString(R.string.block_for_week);
                    break;

                case BlockFor.DAY:
                    blockForText = getString(R.string.block_for_day);
                    break;

                case BlockFor.HOUR:
                    blockForText = getString(R.string.block_for_hour);
                    break;

                case BlockFor.CUSTOM:
                    blockForText = formatBlockFor();
                    break;

                default:
                    throw new IllegalStateException();
            }

            getView().displayBlockFor(blockForText);
        }
    }

    @OnGuiCreated
    private void resolveResonView() {
        if (isGuiReady()) {
            switch (reason) {
                case BlockReason.SPAM:
                    getView().displayReason(getString(R.string.reason_spam));
                    break;
                case BlockReason.IRRELEVANT_MESSAGES:
                    getView().displayReason(getString(R.string.reason_irrelevant_messages));
                    break;
                case BlockReason.STRONG_LANGUAGE:
                    getView().displayReason(getString(R.string.reason_strong_language));
                    break;
                case BlockReason.VERBAL_ABUSE:
                    getView().displayReason(getString(R.string.reason_verbal_abuse));
                    break;
                default:
                    getView().displayReason(getString(R.string.reason_other));
                    break;
            }
        }
    }

    @Override
    protected String tag() {
        return TAG;
    }

    private boolean requestNow;

    private void setRequestNow(boolean requestNow) {
        this.requestNow = requestNow;
        resolveProgressView();
    }

    @OnGuiCreated
    private void resolveProgressView() {
        if (isGuiReady()) {
            if (requestNow) {
                getView().displayProgressDialog(R.string.please_wait, R.string.saving, false);
            } else {
                getView().dismissProgressDialog();
            }
        }
    }

    public void fireButtonSaveClick() {
        setRequestNow(true);

        final int accountId = super.getAccountId();
        final int usetId = currentUser().getId();
        final Date endDate = blockFor.getUnblockingDate();

        final Long endDateUnixtime = nonNull(endDate) ? endDate.getTime() / 1000 : null;

        appendDisposable(interactor.banUser(accountId, groupId, usetId, endDateUnixtime, reason, comment, showCommentToUser)
                .compose(RxUtils.applyCompletableIOToMainSchedulers())
                .subscribe(this::onAddBanComplete, throwable -> onAddBanError(getCauseIfRuntime(throwable))));
    }

    private void onAddBanComplete(){
        setRequestNow(false);
        safeShowToast(getView(), R.string.success, false);

        if(index == users.size() - 1){
            callView(ICommunityBanEditView::goBack);
        } else {
            // switch to next user
            index++;

            resolveUserInfoViews();
        }
    }

    private void onAddBanError(Throwable throwable){
        setRequestNow(false);
        throwable.printStackTrace();
        showError(getView(), throwable);
    }

    public void fireShowCommentCheck(boolean isChecked) {
        showCommentToUser = isChecked;
    }

    public void fireCommentEdit(CharSequence s) {
        this.comment = s.toString();
    }

    public void fireBlockForClick() {
        List<IdOption> options = new ArrayList<>();
        if(blockFor.type == BlockFor.CUSTOM){
            options.add(new IdOption(BLOCK_FOR_UNCHANGED, formatBlockFor()));
        }

        options.add(new IdOption(BlockFor.FOREVER, getString(R.string.block_for_forever)));
        options.add(new IdOption(BlockFor.YEAR, getString(R.string.block_for_year)));
        options.add(new IdOption(BlockFor.MONTH, getString(R.string.block_for_month)));
        options.add(new IdOption(BlockFor.WEEK, getString(R.string.block_for_week)));
        options.add(new IdOption(BlockFor.DAY, getString(R.string.block_for_day)));
        options.add(new IdOption(BlockFor.HOUR, getString(R.string.block_for_hour)));

        getView().displaySelectOptionDialog(REQUEST_CODE_BLOCK_FOR, options);
    }

    private static final int BLOCK_FOR_UNCHANGED = -1;
    private static final int REQUEST_CODE_BLOCK_FOR = 1;
    private static final int REQUEST_CODE_REASON = 2;

    public void fireOptionSelected(int requestCode, IdOption idOption) {
        switch (requestCode){
            case REQUEST_CODE_BLOCK_FOR:
                if(idOption.getId() != BLOCK_FOR_UNCHANGED){
                    blockFor = new BlockFor(idOption.getId());
                    resolveBlockForView();
                } //else not changed
                break;

            case REQUEST_CODE_REASON:
                this.reason = idOption.getId();
                resolveResonView();
                break;
        }
    }

    private String formatBlockFor(){
        Date date = blockFor.getUnblockingDate();
        if(isNull(date)){
            Logger.wtf(TAG, "formatBlockFor, date-is-null???");
            return "NULL";
        }

        String formattedDate = DateFormat.getDateInstance().format(date);
        String formattedTime = DateFormat.getTimeInstance().format(date);
        return getString(R.string.until_date_time, formattedDate, formattedTime);
    }

    public void fireResonClick() {
        List<IdOption> options = new ArrayList<>();

        options.add(new IdOption(BlockReason.SPAM, getString(R.string.reason_spam)));
        options.add(new IdOption(BlockReason.IRRELEVANT_MESSAGES, getString(R.string.reason_irrelevant_messages)));
        options.add(new IdOption(BlockReason.STRONG_LANGUAGE, getString(R.string.reason_strong_language)));
        options.add(new IdOption(BlockReason.VERBAL_ABUSE, getString(R.string.reason_verbal_abuse)));
        options.add(new IdOption(BlockReason.OTHER, getString(R.string.reason_other)));

        getView().displaySelectOptionDialog(REQUEST_CODE_REASON, options);
    }

    public void fireAvatarClick() {
        getView().openProfile(getAccountId(), currentUser());
    }

    private static final class BlockFor {

        static final int FOREVER = 0;
        static final int YEAR = 1;
        static final int MONTH = 2;
        static final int WEEK = 3;
        static final int DAY = 4;
        static final int HOUR = 5;
        static final int CUSTOM = 6;

        Date getUnblockingDate() {
            if (type == CUSTOM) {
                return new Date(customDate * 1000);
            }

            Calendar calendar = Calendar.getInstance();
            switch (type) {
                case YEAR:
                    calendar.add(Calendar.YEAR, 1);
                    break;
                case MONTH:
                    calendar.add(Calendar.MONTH, 1);
                    break;
                case WEEK:
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    break;
                case DAY:
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                case HOUR:
                    calendar.add(Calendar.HOUR, 1);
                    break;

                case FOREVER:
                    return null;
            }

            return calendar.getTime();
        }

        final int type;

        final long customDate;

        BlockFor(int type) {
            this.type = type;
            this.customDate = 0;
        }

        BlockFor(long customDate) {
            this.customDate = customDate;
            this.type = customDate > 0 ? CUSTOM : FOREVER;
        }
    }
}