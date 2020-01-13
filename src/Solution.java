import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    private List<Record> sortedRecords = new LinkedList<>();
    private int numOfPageRows = 0;

    public int method(String[] user_scores, int K) {
        numOfPageRows = K;
        int pageUpdateCount = 0;

        for(String user_score : user_scores) {
            String[] splited = user_score.split(" ");
            String id = splited[0];
            int score = Integer.parseInt(splited[1]);

            pageUpdateCount += updateLeaderBoard(new Record(id, score));
        }

        printLeaderBoard();

        return pageUpdateCount;
    }

    private void printLeaderBoard() {
        System.out.println("----------------------------------------");

        for(Record record : sortedRecords) {
            System.out.println("[" + record.id + "] : " + record.score);
        }

        System.out.println("----------------------------------------");
    }

    // 페이지 내 기록 순위 업데이트
    private int updateLeaderBoard(Record newRecord) {
        // 페이지가 다 채워지지 않고, 현재 최저점보다 낮거나 같은 점수
        if(!hasEmptyRows() && newRecord.score <= scoreOfLastPositionInPage()) {
            return 0;
        }

        // 현재 랭킹 확인
        int currentRank = findUserRank(newRecord.id);

        // 페이지 내 랭킹 변경 있음
        if(isRankChanged(currentRank, newRecord.score)) {
            deleteRecordFromPage(currentRank);
            insertNewRecordToPage(newRecord);
            return 1;

        // 랭킹 변경 없고 점수만 갱신
        } else if(currentRank != -1) {
            updateScore(newRecord);
        }

        return 0;
    }

    // id에 해당하는 페이지 내 순위 반환
    private int findUserRank(String id) {
        final int recordsListSize = sortedRecords.size();

        for(int rank = 0; rank < recordsListSize; rank++) {
            Record record = sortedRecords.get(rank);
            if(record.id.equals(id)) {
                return rank;
            }
        }

        return -1;
    }

    // 페이지 내 최저점 반환
    private int scoreOfLastPositionInPage() {
        if(sortedRecords.isEmpty()) {
            return Integer.MIN_VALUE;
        }

        final int lastIndex = sortedRecords.size() - 1;

        return sortedRecords.get(lastIndex).score;
    }

    // 순위 변경 여부 확인
    private boolean isRankChanged(int currentRank, int newScore) {
        // 이전 기록이 없는데 페이지 내 최저점보다 높은 점수인 경우
        if(currentRank == -1 && newScore > scoreOfLastPositionInPage())
            return true;

        // 이전 기록이 없는데 Leader에 빈 row가 있는 경우
        if(currentRank == -1 && hasEmptyRows())
            return true;

        // 앞 순위의 점수 조회하여 순위 변동이 있는지 확인
        for(int i = 0; i < currentRank; i++) {
            Record record = sortedRecords.get(i);
            if(record.score < newScore)
                return true;
        }

        return false;
    }

    private boolean hasEmptyRows() {
        return sortedRecords.size() < numOfPageRows;
    }

    // 페이지 내 기록 삭제
    private void deleteRecordFromPage(int rank) {
        final int recordsListSize = sortedRecords.size();

        if(rank < 0 || rank >= recordsListSize) {
            return;
        }

        sortedRecords.remove(rank);
    }

    // 페이지 내 기록 추가
    private void insertNewRecordToPage(Record record) {
        sortedRecords.add(record);
        sortedRecords.sort(Comparator.reverseOrder());

        deleteRecordsOutOfPage();
    }

    // 페이지 밖 기록 삭제
    private void deleteRecordsOutOfPage() {
        final int recordsListSize = sortedRecords.size();

        if(recordsListSize <= numOfPageRows)
            return;

        for(int rank = recordsListSize - 1; rank > numOfPageRows - 1; rank--) {
            sortedRecords.remove(rank);
        }
    }

    // 점수만 업데이트
    private void updateScore(Record record) {
        for(Record oldRecord : sortedRecords) {
            if(oldRecord.id.equals(record.id)) {
                oldRecord.score = record.score;
                return;
            }
        }
    }
}

class Record implements Comparable<Record> {
    public String id;
    public int score;

    public Record(String id, int score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public int compareTo(Record o) {
        int targetScore = this.score;

        if(targetScore > o.score) {
            return 1;
        } else if(targetScore < o.score) {
            return -1;
        } else {
            return 0;
        }
    }
}