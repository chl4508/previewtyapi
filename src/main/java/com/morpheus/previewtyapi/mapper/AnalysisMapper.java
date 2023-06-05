package com.morpheus.previewtyapi.mapper;

import com.morpheus.previewtyapi.dto.*;
import com.morpheus.previewtyapi.vo.AnalysisVO;
import com.morpheus.previewtyapi.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AnalysisMapper {

	int selectAnalysisCount(AnalysisVO analysisVO);
	Map<String, Object> selectAnalysisId(AnalysisVO analysisVO);

	int updateFacialWidth(FacialWidthDto facialWidthDto);
	int insertFacialWidth(FacialWidthDto facialWidthDto);

	int updateFacialHeightBalance(FacialHeightBalanceDto facialHeightBalanceDto);
	int insertFacialHeightBalance(FacialHeightBalanceDto facialHeightBalanceDto);

	int updateEyesLength(EyesLengthDto eyesLengthDto);
	int insertEyesLength(EyesLengthDto eyesLengthDto);

	int updateEyeWidthBalance(EyeWidthBalanceDto eyeWidthBalanceDto);
	int insertEyeWidthBalance(EyeWidthBalanceDto eyeWidthBalanceDto);

	int updateNoseLipsLength(NoseLipsLengthDto noseLipsLengthDto);
	int insertNoseLipsLength(NoseLipsLengthDto noseLipsLengthDto);

	int updateNoseLipsAngle(NoseLipsAngleDto noseLipsAngleDto);
	int insertNoseLipsAngle(NoseLipsAngleDto noseLipsAngleDto);

	int updateAsymmetryByVolume(AsymmetryByVolumeDto asymmetryByVolumeDto);
	int insertAsymmetryByVolume(AsymmetryByVolumeDto asymmetryByVolumeDto);

	int updateCurvedLength(CurvedLengthDto curvedLengthDto);
	int insertCurvedLength(CurvedLengthDto curvedLengthDto);

	int updateApProjection(ApProjectionDto apProjectionDto);
	int insertApProjection(ApProjectionDto apProjectionDto);

	int insertUserRecord(UserRecordDto userRecordDto);
	int updateUserRecord(UserRecordDto userRecordDto);


}

