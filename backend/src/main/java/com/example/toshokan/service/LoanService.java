package com.example.toshokan.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.toshokan.entity.Book;
import com.example.toshokan.entity.Loan;
import com.example.toshokan.entity.User;
import com.example.toshokan.repository.BookRepository;
import com.example.toshokan.repository.LoanRepository;
import com.example.toshokan.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanService {
	private LoanRepository loanRepository;

	private BookRepository bookRepository;

	private UserRepository userRepository;

	public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
		this.loanRepository = loanRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}
	

    // ログイン中ユーザー取得
    private User getLoginUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 現在貸し出し中（DTO返す）
    public Page<LoanResponse> getCurrentLoans(Pageable pageable) {

        User user = getLoginUser();

        return loanRepository
                .findByUserAndReturnDateIsNull(user, pageable)
                .map(l -> new LoanResponse(
                        l.getId(),
                        l.getBook().getTitle(),
                        l.getBook().getAuthor(),
                        l.getLoanDate()
                ));
    }

    // 貸し出し履歴（DTO返す）
    public Page<LoanResponse> getHistory(Pageable pageable) {

        User user = getLoginUser();

        return loanRepository
                .findByUserAndReturnDateIsNotNull(user, pageable)
                .map(l -> new LoanResponse(
                        l.getId(),
                        l.getBook().getTitle(),
                        l.getBook().getAuthor(),
                        l.getLoanDate()
                ));
    }

    // 貸し出し処理
    @Transactional
    public boolean borrowBook(Integer bookId) {

        User user = getLoginUser();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStatus() == 1) {
            throw new RuntimeException("Already borrowed");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(null);

        loanRepository.save(loan);

        book.setStatus(1);
        bookRepository.save(book);

        return true;
    }

    // 返却処理
    @Transactional
    public void returnBook(Integer loanId) {

        User user = getLoginUser();

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        Book book = loan.getBook();
        book.setStatus(0);
        bookRepository.save(book);
    }

//	// ログイン中のユーザーを取得する
//	private User getLoginUser() {
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		return userRepository.findByName(username).orElseThrow();
//	}
//
//	// 現在貸し出し中
//	public Page<Loan> getCurrentLoans(Pageable pageable) {
//		User user = getLoginUser();
//		return loanRepository.findByUserAndReturnDateIsNull(user, pageable);
//	}
//
//	// 貸し出し履歴
//	public Page<Loan> getHistory(Pageable pageable) {
//		User user = getLoginUser();
//		return loanRepository.findByUserAndReturnDateIsNotNull(user, pageable);
//
//	}
//
//	// 貸し出し処理
//	@Transactional
//	public boolean borrowBook(Integer bookId) {
//		User user = getLoginUser();
//		
//		if(user==null) {
//			throw new RuntimeException("NOT LOGIN");
//		}
//		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
//		// すでに貸し出し中なら貸せない
//		if (book.getStatus() == 1) {
//			throw new RuntimeException("Already borrowed");
//		}
//		// loan作成
//		Loan loan = new Loan();
//		loan.setUser(user);
//		loan.setBook(book);
//		loan.setLoanDate(LocalDate.now());
//		loan.setReturnDate(null);
//		loanRepository.save(loan);
//
//		// 本のステータス更新
//		book.setStatus(1);
//		bookRepository.save(book);
//
//		return true;
//	}
//
//	// 返却処理
//	@Transactional
//	public void returnBook(Integer loanId) {
//		User user = getLoginUser();
//		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
//		//他人の返却防止
//		if((!loan.getUser().getId().equals(user.getId()))) {
//			throw new RuntimeException("Unauthorized");
//		}
//		
//		//返却処理本体
//		loan.setReturnDate(LocalDate.now());
//		loanRepository.save(loan);
//		
//		//本のステータスを貸し出し中から貸し出し可能にする
//		Book book = loan.getBook();
//		book.setStatus(0);
//		bookRepository.save(book);
//
//	}

//	public boolean borrow(Integer userId, Integer bookId) {
//		Book book = bookRepository.findById(bookId).orElseThrow();
//		User user = userRepository.findById(userId).orElseThrow();
//		
//		if(book.getStatus() == 1) {
//			return false;
//		}
//		
//		Loan loan = new Loan();
//		loan.setUser(user);
//		loan.setBook(book);
//		loan.setLoanDate(LocalDate.now());
//		loan.setReturnDate(null);
//		loanRepository.save(loan);
//		book.setStatus(1);
//		bookRepository.save(book);
//		return true;
//	}

//	public Page<Loan> getCurrentLoans(Pageable pageable){
//		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found."));
//		return loanRepository.findByUserAndReturnDateIsNull(user, pageable);
//	}
//	
//	public Page<Loan> getHistory(Integer userId, Pageable pageable){
//		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found."));
//		return loanRepository.findByUserAndReturnDateIsNotNull(user, pageable);
//	}
//	

}
