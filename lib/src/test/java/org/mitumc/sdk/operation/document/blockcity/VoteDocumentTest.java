package org.mitumc.sdk.operation.document.blockcity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.document.base.Document;

public class VoteDocumentTest {

	@DisplayName("Test document@vote-document")
	@Test
	void testVoteDocument() {
		BlockCityGenerator gn = BlockCityGenerator.get();

		HashMap<String, Object> owner = Generator.randomKeys();
		String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

		HashMap<String, Object> cand0 = Generator.randomKeys();
		String cand0Addr = ((Keys) cand0.get(Keys.ID)).getAddress();
		Candidate candidate0 = gn.candidate(
			cand0Addr,
			"candidate0",
			"manifest0",
			0
		);

		HashMap<String, Object> cand1 = Generator.randomKeys();
		String cand1Addr = ((Keys) cand1.get(Keys.ID)).getAddress();
		Candidate candidate1 = gn.candidate(
			cand1Addr,
			"candidate1",
			"manifest1",
			1
		);

		HashMap<String, Object> account = Generator.randomKeys();
		String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

		Document doc = gn.voteDocument(
			"abcdcvi",
			ownerAddr,
			0,
			"today",
			new Candidate[] { candidate0, candidate1 },
			"i'm boss",
			accountAddr,
			"officetermo"
		);

		assertDoesNotThrow(() -> doc.toBytes());
		assertDoesNotThrow(() -> doc.toDict());
	}

	@DisplayName("Test document@vote-document - wrong owner")
	@Test
	void testVoteDocumentWrongOwner() {
		BlockCityGenerator gn = BlockCityGenerator.get();

		HashMap<String, Object> cand0 = Generator.randomKeys();
		String cand0Addr = ((Keys) cand0.get(Keys.ID)).getAddress();
		Candidate candidate0 = gn.candidate(
			cand0Addr,
			"candidate0",
			"manifest0",
			0
		);

		HashMap<String, Object> cand1 = Generator.randomKeys();
		String cand1Addr = ((Keys) cand1.get(Keys.ID)).getAddress();
		Candidate candidate1 = gn.candidate(
			cand1Addr,
			"candidate1",
			"manifest1",
			1
		);

		HashMap<String, Object> account = Generator.randomKeys();
		String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

		assertThrows(StringFormatException.class, () -> gn.voteDocument(
			"abcdcvi",
			"abcdefg123",
			0,
			"today",
			new Candidate[] { candidate0, candidate1 },
			"i'm boss",
			accountAddr,
			"officetermo"
		));
	}

	@DisplayName("Test document@vote-document - wrong candidate")
	@Test
	void testVoteDocumentWrongCandidate() {
		BlockCityGenerator gn = BlockCityGenerator.get();

		assertThrows(StringFormatException.class, () -> gn.candidate(
			"abcdefg123",
			"candidate0",
			"manifest0",
			0
		));

		HashMap<String, Object> cand0 = Generator.randomKeys();
		String cand0Addr = ((Keys) cand0.get(Keys.ID)).getAddress();

		assertThrows(NumberRangeException.class, () -> gn.candidate(
			cand0Addr,
			"candidate0",
			"manifest0",
			-1
		));
	}

	@DisplayName("Test document@vote-document - wrong account")
	@Test
	void testVoteDocumentWrongAccount() {
		BlockCityGenerator gn = BlockCityGenerator.get();

		HashMap<String, Object> owner = Generator.randomKeys();
		String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

		HashMap<String, Object> cand0 = Generator.randomKeys();
		String cand0Addr = ((Keys) cand0.get(Keys.ID)).getAddress();
		Candidate candidate0 = gn.candidate(
			cand0Addr,
			"candidate0",
			"manifest0",
			0
		);

		HashMap<String, Object> cand1 = Generator.randomKeys();
		String cand1Addr = ((Keys) cand1.get(Keys.ID)).getAddress();
		Candidate candidate1 = gn.candidate(
			cand1Addr,
			"candidate1",
			"manifest1",
			1
		);

		assertThrows(StringFormatException.class, () -> gn.voteDocument(
			"abcdcvi",
			ownerAddr,
			0,
			"today",
			new Candidate[] { candidate0, candidate1 },
			"i'm boss",
			"abcdefg123",
			"officetermo"
		));
	}

	@DisplayName("Test document@vote-document - wrong document id")
	@Test
	void testVoteDocumentWrongDocumentID() {
		BlockCityGenerator gn = BlockCityGenerator.get();

		HashMap<String, Object> owner = Generator.randomKeys();
		String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

		HashMap<String, Object> cand0 = Generator.randomKeys();
		String cand0Addr = ((Keys) cand0.get(Keys.ID)).getAddress();
		Candidate candidate0 = gn.candidate(
			cand0Addr,
			"candidate0",
			"manifest0",
			0
		);

		HashMap<String, Object> cand1 = Generator.randomKeys();
		String cand1Addr = ((Keys) cand1.get(Keys.ID)).getAddress();
		Candidate candidate1 = gn.candidate(
			cand1Addr,
			"candidate1",
			"manifest1",
			1
		);

		HashMap<String, Object> account = Generator.randomKeys();
		String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

		assertThrows(StringFormatException.class, () -> gn.voteDocument(
			"abcdcui",
			ownerAddr,
			0,
			"today",
			new Candidate[] { candidate0, candidate1 },
			"i'm boss",
			accountAddr,
			"officetermo"
		));
	}

	@DisplayName("Test document@vote-document - wrong round")
	@Test
	void testVoteDocumentWrongRound() {
		BlockCityGenerator gn = BlockCityGenerator.get();

		HashMap<String, Object> owner = Generator.randomKeys();
		String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

		HashMap<String, Object> cand0 = Generator.randomKeys();
		String cand0Addr = ((Keys) cand0.get(Keys.ID)).getAddress();
		Candidate candidate0 = gn.candidate(
			cand0Addr,
			"candidate0",
			"manifest0",
			0
		);

		HashMap<String, Object> cand1 = Generator.randomKeys();
		String cand1Addr = ((Keys) cand1.get(Keys.ID)).getAddress();
		Candidate candidate1 = gn.candidate(
			cand1Addr,
			"candidate1",
			"manifest1",
			1
		);

		HashMap<String, Object> account = Generator.randomKeys();
		String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

		assertThrows(NumberRangeException.class, () -> gn.voteDocument(
			"abcdcvi",
			ownerAddr,
			-1,
			"today",
			new Candidate[] { candidate0, candidate1 },
			"i'm boss",
			accountAddr,
			"officetermo"
		));
	}
}
