# GR
지진 대피소 알리미(GR)  
지진 발생 시 사용자 위치 기반 인근 대피소의 위치를 자동으로 출력해주는 어플리케이션  
## 상세 설명
- Broadcast Receiver를 이용하여 기상청으로부터 수신되는 지진 문자를 확인한다  
- 일정 규모 이상의 지진 발생 시 사전에 저장된 연락처로 현재 주소가 담긴 문자를 전송한다  
- API 통신을 통해 해당 지진의 상세 정보와 일정 거리 미만의 대피소를 탐색한다
- 해당 지진 정보는 SQLite를 통해 내장 DB에 저장된다  
- 탐색된 대피소 중 가장 가까운 거리의 대피소를 구글 지도에 마커로 표시한다
- 마커 클릭 시 새 창이 떠 대피소에 대한 상세 정보와 즐겨찾기 등록 여부를 표시한다
- 즐겨찾기(별표) 클릭 시 Shared preference에 저장되어 오프라인 상태에서도 확인가능케 한다
