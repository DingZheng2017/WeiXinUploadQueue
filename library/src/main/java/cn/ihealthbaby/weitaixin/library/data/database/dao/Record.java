package cn.ihealthbaby.weitaixin.library.data.database.dao;

import android.content.Context;

import java.io.File;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "RECORD".
 */
public class Record {

     /**
	 * 自增id
	 */
    private Long id;
    /** Not-null value. */
     /**
	 * 本地记录id,由UUID生成,不带- 对应AdviceItem的jianceId
	 */
    private String localRecordId;
     /**
	 * 用户id
	 */
    private long userId;
     /**
	 * 服务id
	 */
    private Long serviceId;
    /** Not-null value. */
     /**
	 * 用户名
	 */
    private String userName;
    /** Not-null value. */
     /**
	 * 对应AdviceItem的serialNum
	 */
    private String serialNumber;
     /**
	 * 上传状态
	 */
    private int uploadState;
     /**
	 * 监测开始时间,对应AdviceItem的testTime
	 */
    private java.util.Date recordStartTime;
     /**
	 * 孕周文本,对应AdviceItem的gestationalWeeks
 格式30周+4	 */
    private String gestationalWeeks;
     /**
	 * 监测时长,单位秒,对应AdviceItem的testTimeLong
	 */
    private Integer duration;
     /**
	 * 监测记录的数据结构,JSON格式
	 */
    private String recordData;
     /**
	 * 本地音频文件路径
	 */
    private String soundPath;
     /**
	 * 监护心情,对应AdviceItem的feelingId
	 */
    private Integer feelingId;
     /**
	 * 监护心情,对应AdviceItem的feeling
	 */
    private String feelingString;
     /**
	 * 监护目的,对应AdviceItem的puposeId
	 */
    private Integer purposeId;
     /**
	 * 监护目的,对应AdviceItem的pupose
	 */
    private String purposeString;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RecordDao myDao;


    // KEEP FIELDS - put your custom fields here
	//需要讨论
//	private static final int ONE = 0x1;
//	public static final int UPLOAD_STATE_HAS_DATA = ONE << 0;
//  public static final int UPLOAD_STATE_HAS_SOUND = ONE << 1;
//	public static final int UPLOAD_STATE_IS_UPLOADING = ONE << 2;
//	public static final int UPLOAD_STATE_IS_UPLOADED = ONE << 3;
//	public static final int UPLOAD_STATE_DATA = UPLOAD_STATE_HAS_DATA;
//	public static final int UPLOAD_STATE_SOUND = UPLOAD_STATE_HAS_SOUND;
	public static final int UPLOAD_STATE_LOCAL =  1;
	public static final int UPLOAD_STATE_UPLOADING = 2;
    public static final int UPLOAD_STATE_CLOUD = 4;
    //
    public static final int PURPOSE_FM_NORMAL =1;
    public static final int PURPOSE_FM = 2;
    public static final int PURPOSE_FM_HIGH_FRENQUENCY = 3;
    public static final int PURPOSE_FM_DECREASE = 4;
    public static final int PURPOSE_FM_OTHER = 5;
	//
    public static final int FEELING_NORMAL = 3;
    public static final int FEELING_DEPRESSED = 2;
    public static final int FEELING_HAPPY = 1;


//	public static final int SERVICE_STATUS_ = 5;
//	//0"问医生", 1"等待回复", 2"已回复", 3"需上传"
//	public static final int SERVICE_STATUS_ASK_DOCTOR = 0;
//	public static final int SERVICE_STATUS_WAIT_REPLY = 1;
//	public static final int SERVICE_STATUS_REPLYED = 2;
//	public static final int SERVICE_STATUS_LOCAL = 3;
    // KEEP FIELDS END

    public Record() {
    }

    public Record(Long id) {
        this.id = id;
    }

    public Record(Long id, String localRecordId, long userId, Long serviceId, String userName, String serialNumber, int uploadState, java.util.Date recordStartTime, String gestationalWeeks, Integer duration, String recordData, String soundPath, Integer feelingId, String feelingString, Integer purposeId, String purposeString) {
        this.id = id;
        this.localRecordId = localRecordId;
        this.userId = userId;
        this.serviceId = serviceId;
        this.userName = userName;
        this.serialNumber = serialNumber;
        this.uploadState = uploadState;
        this.recordStartTime = recordStartTime;
        this.gestationalWeeks = gestationalWeeks;
        this.duration = duration;
        this.recordData = recordData;
        this.soundPath = soundPath;
        this.feelingId = feelingId;
        this.feelingString = feelingString;
        this.purposeId = purposeId;
        this.purposeString = purposeString;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRecordDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getLocalRecordId() {
        return localRecordId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLocalRecordId(String localRecordId) {
        this.localRecordId = localRecordId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /** Not-null value. */
    public String getUserName() {
        return userName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Not-null value. */
    public String getSerialNumber() {
        return serialNumber;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getUploadState() {
        return uploadState;
    }

    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }

    public java.util.Date getRecordStartTime() {
        return recordStartTime;
    }

    public void setRecordStartTime(java.util.Date recordStartTime) {
        this.recordStartTime = recordStartTime;
    }

    public String getGestationalWeeks() {
        return gestationalWeeks;
    }

    public void setGestationalWeeks(String gestationalWeeks) {
        this.gestationalWeeks = gestationalWeeks;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRecordData() {
        return recordData;
    }

    public void setRecordData(String recordData) {
        this.recordData = recordData;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public Integer getFeelingId() {
        return feelingId;
    }

    public void setFeelingId(Integer feelingId) {
        this.feelingId = feelingId;
    }

    public String getFeelingString() {
        return feelingString;
    }

    public void setFeelingString(String feelingString) {
        this.feelingString = feelingString;
    }

    public Integer getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Integer purposeId) {
        this.purposeId = purposeId;
    }

    public String getPurposeString() {
        return purposeString;
    }

    public void setPurposeString(String purposeString) {
        this.purposeString = purposeString;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Record{");
        sb.append("id=").append(id);
        sb.append(", localRecordId='").append(localRecordId).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", serviceId=").append(serviceId);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", recordStartTime=").append(recordStartTime);
        sb.append(", gestationalWeeks='").append(gestationalWeeks).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", recordData='").append(recordData).append('\'');
        sb.append(", soundPath='").append(soundPath).append('\'');
        sb.append(", uploadState=").append(uploadState);
        sb.append(", feelingId=").append(feelingId);
        sb.append(", feelingString='").append(feelingString).append('\'');
        sb.append(", purposeId=").append(purposeId);
        sb.append(", purposeString='").append(purposeString).append('\'');
        sb.append('}');
        return sb.toString();
    }
    // KEEP METHODS END

}
